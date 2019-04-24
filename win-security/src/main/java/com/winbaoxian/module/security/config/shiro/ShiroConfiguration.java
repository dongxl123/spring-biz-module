package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.service.WinSecurityRoleService;
import com.winbaoxian.module.security.service.WinSecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-11-27 18:06
 */
@Configuration
@Slf4j
public class ShiroConfiguration {

    @Autowired
    private List<Realm> realmList;
    @Autowired
    private SessionManager sessionManager;

    @Bean
    @ConditionalOnMissingBean(SecurityManager.class)
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realmList
        securityManager.setRealms(realmList);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Configuration
    public static class SessionManagerConfiguration {
        @Autowired
        private SessionDAO sessionDAO;
        @Autowired
        private Cookie cookie;
        @Autowired(required = false)
        private CacheManager cacheManager;

        @Bean
        @ConditionalOnMissingBean(SessionManager.class)
        public SessionManager sessionManager() {
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionIdCookie(cookie);
            sessionManager.setSessionIdUrlRewritingEnabled(false);
            sessionManager.setSessionDAO(sessionDAO);
            sessionManager.setCacheManager(cacheManager);
            return sessionManager;
        }
    }

    @Configuration
    public static class CookieConfiguration {

        @Bean
        @ConditionalOnMissingBean(Cookie.class)
        public Cookie cookie() {
            Cookie cookie = new SimpleCookie(WinSecurityConstant.SHIRO_COOKIE_NAME);
            cookie.setHttpOnly(true);
            return cookie;
        }
    }

    @Configuration
    public static class SessionDAOConfiguration {

        @Bean
        @ConditionalOnMissingBean(SessionDAO.class)
        public SessionDAO sessionDAO() {
            return new MemorySessionDAO();
        }
    }

    @Configuration
    public static class RealmConfiguration {
        @Resource
        private WinSecurityUserService winSecurityUserService;
        @Resource
        private WinSecurityRoleService winSecurityRoleService;
        @Resource
        private WinSecurityResourceService winSecurityResourceService;

        @Bean
        public Realm realm() {
            return new WinSecurityRealm(winSecurityUserService, winSecurityRoleService, winSecurityResourceService);
        }
    }

}
