package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.filter.WinSecurityUrlFilter;
import com.winbaoxian.module.security.service.WinSecurityAccessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-11-27 18:06
 */
@Configuration
@Slf4j
public class ShiroConfiguration {

    @PostConstruct
    public void init() {
        try {
            SecurityUtils.setSecurityManager(securityManager());
        } catch (Exception e) {
            log.error("WinSecurity: SecurityUtils.setSecurityManager failed");
        }
        log.info("WinSecurity: SecurityUtils.setSecurityManager successful");
    }

    @Resource
    private WinSecurityRealm winSecurityRealm;
    @Resource
    private WinSecurityAccessService winSecurityAccessService;

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(winSecurityRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * @return
     * @see org.apache.shiro.web.filter.mgt.DefaultFilter
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //拦截
        filterChainDefinitionMap.put("/**", "urlFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 获取filters
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 将自定义的Filter注入shiroFilter中
        filters.put("urlFilter", new WinSecurityUrlFilter(winSecurityAccessService));
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        return shiroFilterFactoryBean;
    }

}
