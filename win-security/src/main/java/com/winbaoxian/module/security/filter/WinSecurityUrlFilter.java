package com.winbaoxian.module.security.filter;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.utils.MemoryExpirationCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Date;
import java.util.List;


/**
 * @author dongxuanliang252
 * @date 2018-11-28 10:10
 */
public class WinSecurityUrlFilter extends PathMatchingFilter {

    private static final Logger log = LoggerFactory.getLogger(WinSecurityUrlFilter.class);

    private static final String RESOURCE_CACHE_KEY = "ALL_RESOURCE_LIST";
    private static final int REFRESH_CACHE_TIME = 60;
    private WinSecurityResourceService winSecurityResourceService;

    private MemoryExpirationCache<String, List<WinSecurityResourceDTO>> cache = new MemoryExpirationCache<>(true, REFRESH_CACHE_TIME);

    public WinSecurityUrlFilter() {
    }

    public WinSecurityUrlFilter(WinSecurityResourceService winSecurityResourceService) {
        this.winSecurityResourceService = winSecurityResourceService;
    }

    public void setWinSecurityResourceService(WinSecurityResourceService winSecurityResourceService) {
        this.winSecurityResourceService = winSecurityResourceService;
    }

    private boolean isAccessAllowed(ServletRequest request, ServletResponse response) throws Exception {
        List<WinSecurityResourceDTO> resourceList = cache.get(RESOURCE_CACHE_KEY);
        if (CollectionUtils.isEmpty(resourceList)) {
            resourceList = winSecurityResourceService.getAllValidAccessResourceList();
            cache.put(RESOURCE_CACHE_KEY, resourceList);
            log.info("WinSecurityUrlFilter, 时间:{}, 从数据库获取数据", new Date());
        }
        String path = WebUtils.toHttp(request).getRequestURI();
        log.info("WinSecurityUrlFilter, 请求路径, path:{}", path);
        Long resourceId = null;
        if (CollectionUtils.isNotEmpty(resourceList)) {
            for (WinSecurityResourceDTO resourceDTO : resourceList) {
                if (StringUtils.isBlank(resourceDTO.getAjaxUrls())) {
                    continue;
                }
                String[] ajaxUrlPatterns = StringUtils.split(resourceDTO.getAjaxUrls(), ",");
                for (String ajaxUrlPattern : ajaxUrlPatterns) {
                    if (pathsMatch(ajaxUrlPattern, path)) {
                        log.info("WinSecurityUrlFilter, 匹配到路径, path:{}, urlPattern:{}", path, ajaxUrlPattern);
                        resourceId = resourceDTO.getId();
                        break;
                    }
                }
            }
        }
        Subject subject = SecurityUtils.getSubject();
        //无权限配置
        if (resourceId == null) {
            if (isSpecialResource(path)) {
                //无权限配置（特殊路径），登录用户有权访问
                if (subject != null && subject.isAuthenticated()) {
                    return true;
                }
                return false;
            }
            //无权限配置（非特殊路径），直接通过
            return true;
        }
        //有权限配置，登录用户有该权限通过
        if (subject != null && subject.isAuthenticated()) {
            return subject.isPermitted(String.valueOf(resourceId));
        }
        return false;
    }

    private boolean isSpecialResource(String path) {
        for (String specialUrl : WinSecurityConstant.RESOURCE_SPECIAL_URL_ARRAY) {
            if (pathsMatch(specialUrl, path)) {
                return true;
            }
        }
        return false;
    }


    private boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            WebUtils.toHttp(response).sendError(403);
        } else {
            WebUtils.toHttp(response).sendError(401);
        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return isAccessAllowed(request, response) || onAccessDenied(request, response);
    }

}
