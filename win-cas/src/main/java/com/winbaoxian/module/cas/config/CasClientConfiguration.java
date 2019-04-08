package com.winbaoxian.module.cas.config;

import com.winbaoxian.module.cas.adapter.CasClientConfigurer;
import com.winbaoxian.module.cas.annotation.EnableWinCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * {@link EnableWinCasClient EnableWinCasClient} meta annotation.
 *
 * @author Dmitriy Kopylenko
 * @see EnableWinCasClient
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(CasClientConfigurationProperties.class)
public class CasClientConfiguration {

    @Autowired
    private CasClientConfigurationProperties configProps;

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
    @ConditionalOnProperty(value = "cas.use-single-signOut", matchIfMissing = true)
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    @ConditionalOnProperty(value = "cas.use-single-signOut", matchIfMissing = true)
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), this.configProps.getServerUrlPrefix());
        filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), this.configProps.getClientHostUrl());
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    /**
     * 登录校验
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean casAuthenticationFilter() {
        final FilterRegistrationBean authnFilter = new FilterRegistrationBean();
        authnFilter.setFilter(new AuthenticationFilter());
        authnFilter.setOrder(2);
        final Map<String, String> initParams = new HashMap<>(2);
        initParams.put(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), this.configProps.getServerLoginUrl());
        initParams.put(ConfigurationKeys.SERVER_NAME.getName(), this.configProps.getClientHostUrl());
        initParams.put(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS.getName(), "com.winbaoxian.module.cas.strategy.AjaxAuthRedirectStrategy");
        authnFilter.setInitParameters(initParams);
        List<String> authenticationUrlPatterns = this.configProps.getAuthenticationUrlPatterns();
        if (authenticationUrlPatterns.size() > 0) {
            authnFilter.setUrlPatterns(authenticationUrlPatterns);
        }
        if (this.configProps.getGateway() != null) {
            authnFilter.getInitParameters().put(ConfigurationKeys.GATEWAY.getName(), String.valueOf(this.configProps.getGateway()));
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
        final FilterRegistrationBean validationFilter = new FilterRegistrationBean();
        final Filter targetCasValidationFilter;
        switch (this.configProps.getValidationType()) {
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
        final Map<String, String> initParams = new HashMap<>(2);
        initParams.put(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), this.configProps.getServerUrlPrefix());
        initParams.put(ConfigurationKeys.SERVER_NAME.getName(), this.configProps.getClientHostUrl());
        validationFilter.setInitParameters(initParams);
        List<String> validationUrlPatterns = this.configProps.getValidationUrlPatterns();
        if (validationUrlPatterns.size() > 0) {
            validationFilter.setUrlPatterns(validationUrlPatterns);
        }
        if (this.configProps.getUseSession() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.USE_SESSION.getName(), String.valueOf(this.configProps.getUseSession()));
        }
        if (this.configProps.getRedirectAfterValidation() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.REDIRECT_AFTER_VALIDATION.getName(), String.valueOf(this.configProps.getRedirectAfterValidation()));
        }

        //Proxy tickets validation
        if (this.configProps.getAcceptAnyProxy() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.ACCEPT_ANY_PROXY.getName(), String.valueOf(this.configProps.getAcceptAnyProxy()));
        }
        if (this.configProps.getAllowedProxyChains().size() > 0) {
            validationFilter.getInitParameters().put(ConfigurationKeys.ALLOWED_PROXY_CHAINS.getName(),
                    StringUtils.collectionToDelimitedString(this.configProps.getAllowedProxyChains(), " "));
        }
        if (this.configProps.getProxyCallbackUrl() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.PROXY_CALLBACK_URL.getName(), this.configProps.getProxyCallbackUrl());
        }
        if (this.configProps.getProxyReceptorUrl() != null) {
            validationFilter.getInitParameters().put(ConfigurationKeys.PROXY_RECEPTOR_URL.getName(), this.configProps.getProxyReceptorUrl());
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
        final FilterRegistrationBean reqWrapperFilter = new FilterRegistrationBean();
        reqWrapperFilter.setFilter(new HttpServletRequestWrapperFilter());
        if (this.configProps.getRequestWrapperUrlPatterns().size() > 0) {
            reqWrapperFilter.setUrlPatterns(this.configProps.getRequestWrapperUrlPatterns());
        }
        reqWrapperFilter.setOrder(4);

        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureHttpServletRequestWrapperFilter(reqWrapperFilter);
        }
        return reqWrapperFilter;
    }


}
