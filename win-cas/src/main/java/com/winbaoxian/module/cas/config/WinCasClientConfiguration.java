package com.winbaoxian.module.cas.config;

import com.winbaoxian.module.cas.adapter.WinCasClientConfigurer;
import com.winbaoxian.module.cas.annotation.EnableWinCasClient;
import com.winbaoxian.module.cas.constant.WinCasConstant;
import com.winbaoxian.module.cas.filter.WinCasLogoutFilter;
import org.apache.commons.collections.CollectionUtils;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.*;


/**
 * Configuration class providing default CAS client infrastructure filters.
 * This config facility is typically imported into Spring's Application Context via
 * {@link EnableWinCasClient EnableWinCasClient} meta annotation.
 *
 * @author Dmitriy Kopylenko
 * @see EnableWinCasClient
 * @since 1.0.0
 */
@Configuration
public class WinCasClientConfiguration {

    @Autowired
    private WinCasClientConfigurationProperties configProps;

    private WinCasClientConfigurer casClientConfigurer;

    @Autowired(required = false)
    void setConfigurers(Collection<WinCasClientConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        if (configurers.size() > 1) {
            throw new IllegalStateException(configurers.size() + " implementations of " +
                    "WinCasClientConfigurer were found when only 1 was expected. " +
                    "Refactor the config such that WinCasClientConfigurer is " +
                    "implemented only once or not at all.");
        }
        this.casClientConfigurer = configurers.iterator().next();
    }

    /**
     * 用于实现单点登出功能
     */
    @Bean
    @ConditionalOnProperty(name = "cas.use-single-sign-out", havingValue = "true", matchIfMissing = true)
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> casSingleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    @ConditionalOnProperty(name = "cas.use-single-sign-out", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean casSingleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), configProps.getServerUrlPrefix());
        filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), configProps.getClientHostUrl());
        filterRegistration.setOrder(0);
        filterRegistration.setMatchAfter(true);
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureSingleSignOutFilter(filterRegistration);
        }
        return filterRegistration;
    }

    @Bean
    public FilterRegistrationBean winCasLogoutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new WinCasLogoutFilter());
        filterRegistration.addUrlPatterns(WinCasConstant.API_LOGOUT_URL);
        filterRegistration.setOrder(1);
        filterRegistration.setMatchAfter(true);
        return filterRegistration;
    }

    /**
     * 登录校验
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean casAuthenticationFilter() {
        FilterRegistrationBean authnFilter = new FilterRegistrationBean();
        authnFilter.setFilter(new AuthenticationFilter());
        authnFilter.setOrder(2);
        authnFilter.setMatchAfter(true);
        Map<String, String> initParams = new HashMap<>(2);
        initParams.put(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), configProps.getServerLoginUrl());
        initParams.put(ConfigurationKeys.SERVICE.getName(), String.format("%s/api/winCas/auth?callback=", configProps.getClientHostUrl()));
        initParams.put(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS.getName(), "com.winbaoxian.module.cas.strategy.AjaxAuthRedirectStrategy");
        authnFilter.setInitParameters(initParams);
        List<String> authenticationUrlPatterns = configProps.getAuthenticationUrlPatterns();
        if (CollectionUtils.isNotEmpty(authenticationUrlPatterns)) {
            authnFilter.setUrlPatterns(authenticationUrlPatterns);
        }
        List<String> allUrlIgnorePatterns = new ArrayList<>();
        allUrlIgnorePatterns.add(WinCasConstant.API_LOGOUT_URL);
        List<String> authenticationUrlIgnorePatterns = configProps.getAuthenticationUrlIgnorePatterns();
        if (CollectionUtils.isNotEmpty(authenticationUrlIgnorePatterns)) {
            allUrlIgnorePatterns.addAll(authenticationUrlIgnorePatterns);
        }
        authnFilter.getInitParameters().put(ConfigurationKeys.IGNORE_PATTERN.getName(), String.join("|", allUrlIgnorePatterns));
        if (configProps.getGateway() != null) {
            authnFilter.getInitParameters().put(ConfigurationKeys.GATEWAY.getName(), String.valueOf(configProps.getGateway()));
        }
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureAuthenticationFilter(authnFilter);
        }
        return authnFilter;
    }

    /**
     * ticket校验
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean casValidationFilter() {
        FilterRegistrationBean validationFilter = new FilterRegistrationBean();
        Filter targetCasValidationFilter;
        switch (configProps.getValidationType()) {
            case CAS:
                targetCasValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
                break;
            case CAS3:
                targetCasValidationFilter = new Cas30ProxyReceivingTicketValidationFilter();
                break;
            default:
                throw new IllegalStateException("Unknown CAS validation type");
        }
        validationFilter.setFilter(targetCasValidationFilter);
        validationFilter.setOrder(3);
        validationFilter.setMatchAfter(true);
        final Map<String, String> initParams = new HashMap<>(2);
        initParams.put(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), configProps.getServerUrlPrefix());
        initParams.put(ConfigurationKeys.SERVER_NAME.getName(), configProps.getClientHostUrl());
        validationFilter.setInitParameters(initParams);
        List<String> validationUrlPatterns = configProps.getValidationUrlPatterns();
        if (validationUrlPatterns.size() > 0) {
            validationFilter.setUrlPatterns(validationUrlPatterns);
        }
        if (configProps.getUseSession() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.USE_SESSION.getName(), String.valueOf(configProps.getUseSession()));
        }
        if (configProps.getRedirectAfterValidation() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.REDIRECT_AFTER_VALIDATION.getName(), String.valueOf(configProps.getRedirectAfterValidation()));
        }

        //Proxy tickets validation
        if (configProps.getAcceptAnyProxy() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.ACCEPT_ANY_PROXY.getName(), String.valueOf(configProps.getAcceptAnyProxy()));
        }
        if (configProps.getAllowedProxyChains().size() > 0) {
            validationFilter.getInitParameters().put(ConfigurationKeys.ALLOWED_PROXY_CHAINS.getName(),
                    StringUtils.collectionToDelimitedString(configProps.getAllowedProxyChains(), " "));
        }
        if (configProps.getProxyCallbackUrl() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.PROXY_CALLBACK_URL.getName(), configProps.getProxyCallbackUrl());
        }
        if (configProps.getProxyReceptorUrl() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.PROXY_RECEPTOR_URL.getName(), configProps.getProxyReceptorUrl());
        }
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureValidationFilter(validationFilter);
        }
        return validationFilter;
    }

    /**
     * http wrapper
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean casHttpServletRequestWrapperFilter() {
        FilterRegistrationBean reqWrapperFilter = new FilterRegistrationBean();
        reqWrapperFilter.setFilter(new HttpServletRequestWrapperFilter());
        if (configProps.getRequestWrapperUrlPatterns().size() > 0) {
            reqWrapperFilter.setUrlPatterns(configProps.getRequestWrapperUrlPatterns());
        }
        reqWrapperFilter.setOrder(4);
        reqWrapperFilter.setMatchAfter(true);
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureHttpServletRequestWrapperFilter(reqWrapperFilter);
        }
        return reqWrapperFilter;
    }

}
