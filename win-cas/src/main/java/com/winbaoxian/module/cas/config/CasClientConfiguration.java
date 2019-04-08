package com.winbaoxian.module.cas.config;

import com.winbaoxian.module.cas.adapter.CasClientConfigurer;
import com.winbaoxian.module.cas.annotation.EnableCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Configuration class providing default CAS client infrastructure filters.
 * This config facility is typically imported into Spring's Application Context via
 * {@link EnableCasClient EnableCasClient} meta annotation.
 *
 * @author Dmitriy Kopylenko
 * @see EnableCasClient
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(CasClientConfigurationProperties.class)
public class CasClientConfiguration {

    @Autowired
    private CasClientConfigurationProperties casProperties;
    
    private static boolean casEnabled = true;

    private CasClientConfigurer casClientConfigurer;

    @Autowired(required = false)
    void setConfigurers(Collection<CasClientConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        if (configurers.size() > 1) {
            throw new IllegalStateException(configurers.size() + " implementations of " +
                    "CasClientConfigurer were found when only 1 was expected. " +
                    "Refactor the config such that CasClientConfigurer is " +
                    "implemented only once or not at all.");
        }
        this.casClientConfigurer = configurers.iterator().next();
    }


    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casEnabled);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
//    @Bean
//    public FilterRegistrationBean logOutFilter() {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        LogoutFilter logoutFilter = new LogoutFilter(casProperties.getCasServerUrlPrefix() + "/logout?service=" + casProperties.getClientHostUrl(), new SecurityContextLogoutHandler());
//        filterRegistration.setFilter(logoutFilter);
//        filterRegistration.setEnabled(casEnabled);
//        filterRegistration.addUrlPatterns("/logout");
//        filterRegistration.addInitParameter("casServerUrlPrefix", casProperties.getCasServerUrlPrefix());
//        filterRegistration.addInitParameter("serverName", casProperties.getClientHostUrl());
//        filterRegistration.setOrder(Integer.MIN_VALUE+1);
//        return filterRegistration;
//    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.setEnabled(casEnabled);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.addInitParameter("casServerUrlPrefix", casProperties.getServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", casProperties.getClientHostUrl());
        filterRegistration.setOrder(3);
        return filterRegistration;
    }

    /**
     * 该过滤器负责用户的认证工作
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AuthenticationFilter());
        filterRegistration.setEnabled(casEnabled);

//        filterRegistration.addUrlPatterns("/claim/*");
        filterRegistration.addUrlPatterns("/auth");
        filterRegistration.addUrlPatterns("/user/*");

        //casServerLoginUrl:cas服务的登陆url
        filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), casProperties.getServerLoginUrl().trim());
        filterRegistration.addInitParameter(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS.getName(), AuthenticationRedirectStrategy.class.getName());
//        filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), casProperties.getClientHostUrl().trim());
       // filterRegistration.addInitParameter(ConfigurationKeys.SERVICE.getName(), casProperties.getServerAuthPath() );
//        filterRegistration.addInitParameter("redirectAfterValidation", "true");
        filterRegistration.setOrder(4);
        return filterRegistration;
    }




    /**
     * 该过滤器负责对Ticket的校验工作
     */
    @Bean
    public FilterRegistrationBean cas30ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        Cas30ProxyReceivingTicketValidationFilter cas30ProxyReceivingTicketValidationFilter = new Cas30ProxyReceivingTicketValidationFilter();
        cas30ProxyReceivingTicketValidationFilter.setServerName(casProperties.getClientHostUrl());
        filterRegistration.setFilter(cas30ProxyReceivingTicketValidationFilter);
        filterRegistration.setEnabled(casEnabled);


        filterRegistration.addUrlPatterns("/*");
//        filterRegistration.addInitParameter();
//        filterRegistration.addInitParameter("useSession", "true");
//        filterRegistration.addInitParameter("redirectAfterValidation", "true");
        filterRegistration.addInitParameter("casServerUrlPrefix", casProperties.getServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", casProperties.getClientHostUrl());

        filterRegistration.setOrder(5);
        return filterRegistration;
    }


    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new HttpServletRequestWrapperFilter());
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(Integer.MIN_VALUE);
        return filterRegistration;
    }


}
