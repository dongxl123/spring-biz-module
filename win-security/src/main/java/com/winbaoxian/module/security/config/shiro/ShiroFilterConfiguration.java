package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.filter.WinSecurityUrlFilter;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 14:44
 */
@Configuration
@Slf4j
public class ShiroFilterConfiguration {

    @Autowired
    private SecurityManager securityManager;

    @PostConstruct
    public void init() {
        try {
            SecurityUtils.setSecurityManager(securityManager);
        } catch (Exception e) {
            log.error("WinSecurity: SecurityUtils.setSecurityManager failed");
        }
        log.info("WinSecurity: SecurityUtils.setSecurityManager successful");
    }

    @Resource
    private WinSecurityResourceService winSecurityResourceService;

    /**
     * @return
     * @see org.apache.shiro.web.filter.mgt.DefaultFilter
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //拦截
        filterChainDefinitionMap.put("/**", "urlFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 获取filters
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 将自定义的Filter注入shiroFilter中
        filters.put("urlFilter", new WinSecurityUrlFilter(winSecurityResourceService));
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
}

