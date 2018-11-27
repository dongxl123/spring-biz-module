package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-11-27 18:06
 */
@Configuration
@Slf4j
public class WinSecurityConfiguration {

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

    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 获取filters
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 将自定义 的FormAuthenticationFilter注入shiroFilter中
        filters.put("shiroFilter", new ShiroFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        return shiroFilterFactoryBean;
    }
}
