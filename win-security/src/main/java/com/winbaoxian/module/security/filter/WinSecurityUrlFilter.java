package com.winbaoxian.module.security.filter;

import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.utils.MemoryExpirationCache;
import org.apache.commons.collections.CollectionUtils;
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

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response) throws Exception {

        List<WinSecurityResourceDTO> resourceList = cache.get(RESOURCE_CACHE_KEY);
        if (CollectionUtils.isEmpty(resourceList)) {
            resourceList = winSecurityResourceService.getAllValidResourceList();
            cache.put(RESOURCE_CACHE_KEY, resourceList);
            log.info("WinSecurityUrlFilter, 时间:{}, 从数据库获取数据", new Date());
        }
        Long resourceId = null;
        if (CollectionUtils.isNotEmpty(resourceList)) {
            String path = WebUtils.toHttp(request).getRequestURI();
            for (WinSecurityResourceDTO resourceDTO : resourceList) {
                if (pathsMatch(resourceDTO.getValue(), path)) {
                    resourceId = resourceDTO.getId();
                    break;
                }
            }
        }
        if (resourceId == null) {
            return true;
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            return subject.isPermitted(String.valueOf(resourceId));
        }
        return false;
    }


    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.toHttp(response).sendError(401);
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return this.isAccessAllowed(request, response) || this.onAccessDenied(request, response);
    }

}
