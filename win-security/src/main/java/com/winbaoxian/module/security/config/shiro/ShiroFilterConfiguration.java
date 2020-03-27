package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.filter.WinSecurityUrlFilter;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

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

    @Autowired
    private CasConfig casConfig;


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
    @Bean(name = WinSecurityConstant.BEAN_NAME_SHIRO_FILTER)
    public ShiroFilterFactoryBean shiroFilter() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //拦截
        filterChainDefinitionMap.put(casConfig.getWinCasClientConfigurationProperties().getProxyCallbackUrl(), "callbackFilter");
        if (casConfig.getWinCasClientConfigurationProperties().getAuthenticationUrlIgnorePatterns() != null) {
            casConfig.getWinCasClientConfigurationProperties().getAuthenticationUrlIgnorePatterns().stream()
                    .forEach(pattern->filterChainDefinitionMap.put(pattern,"anon"));
        }
        filterChainDefinitionMap.put(casConfig.getWinCasClientConfigurationProperties().getServerLogoutUrl(), "logout");
        filterChainDefinitionMap.put("/**", "securityFilter");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 获取filters
        shiroFilterFactoryBean.setFilters(
                new LinkedHashMap<String, Filter>() {{
                    put("callbackFilter", casConfig.callbackFilter());
                    put("securityFilter", casConfig.mySecurityFilter());
                    put("logout", casConfig.logoutFilter());
                }}
        );
        // 将自定义的Filter注入shiroFilter中
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }



    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy(WinSecurityConstant.BEAN_NAME_SHIRO_FILTER));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.setName(WinSecurityConstant.BEAN_NAME_SHIRO_FILTER);
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(10);
        filterRegistration.setMatchAfter(true);
        return filterRegistration;
    }

    @Bean FilterRegistrationBean urlFilterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new WinSecurityUrlFilter(winSecurityResourceService));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.setName("urlFilter");
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(20);
        filterRegistration.setMatchAfter(true);
        return filterRegistration;
    }

}

