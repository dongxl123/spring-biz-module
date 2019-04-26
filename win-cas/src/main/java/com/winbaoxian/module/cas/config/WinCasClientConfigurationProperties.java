package com.winbaoxian.module.cas.config;

import com.winbaoxian.module.cas.enums.ValidationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link ConfigurationProperties} for CAS Java client filters.
 * <p>
 * Will be used to customize CAS filters via simple properties or YAML files in standard Spring Boot PropertySources.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cas")
@PropertySource(value = {"classpath:win-cas.properties"}, ignoreResourceNotFound = true)
public class WinCasClientConfigurationProperties {

    /**
     * CAS server URL E.g. https://example.com/cas or https://cas.example. Required.
     */
    @Value("#{@environment['cas.server-url-prefix'] ?: null }")
    private String serverUrlPrefix;

    /**
     * CAS server login URL E.g. https://example.com/cas/login or https://cas.example/login. Required.
     */
    @Value("#{@environment['cas.server-login-url'] ?: null }")
    private String serverLoginUrl;

    /**
     * CAS-protected client application host URL E.g. https://myclient.example.com Required.
     */
    @Value("#{@environment['cas.client-host-url'] ?: null }")
    private String clientHostUrl;

    /**
     * List of URL patterns protected by CAS authentication filter.
     */
    @Value("#{@environment['cas.authentication-url-patterns'] ?: null }")
    private List<String> authenticationUrlPatterns = new ArrayList<>();

    /**
     * List of URL ignore patterns protected by CAS authentication filter.
     */
    @Value("#{@environment['cas.authentication-url-ignore-patterns'] ?: null }")
    private List<String> authenticationUrlIgnorePatterns = new ArrayList<>();

    /**
     * List of URL patterns protected by CAS validation filter.
     */
    @Value("#{@environment['cas.validation-url-patterns'] ?: null }")
    private List<String> validationUrlPatterns = new ArrayList<>();

    /**
     * List of URL patterns protected by CAS request wrapper filter.
     */
    @Value("#{@environment['cas.request-wrapper-url-patterns'] ?: null }")
    private List<String> requestWrapperUrlPatterns = new ArrayList<>();

    /**
     * Authentication filter gateway parameter.
     */
    @Value("#{@environment['cas.gateway'] ?: null }")
    private Boolean gateway;

    /**
     * Validation filter useSession parameter.
     */
    @Value("#{@environment['cas.use-session'] ?: null }")
    private Boolean useSession;

    /**
     * Validation filter redirectAfterValidation.
     */
    @Value("#{@environment['cas.redirect-after-validation'] ?: null }")
    private Boolean redirectAfterValidation;

    /**
     * Cas20ProxyReceivingTicketValidationFilter acceptAnyProxy parameter.
     */
    @Value("#{@environment['cas.accept-any-proxy'] ?: null }")
    private Boolean acceptAnyProxy;

    /**
     * Cas20ProxyReceivingTicketValidationFilter allowedProxyChains parameter.
     */
    @Value("#{@environment['cas.allowed-proxy-chains'] ?: null }")
    private List<String> allowedProxyChains = new ArrayList<>();

    /**
     * Cas20ProxyReceivingTicketValidationFilter proxyCallbackUrl parameter.
     */
    @Value("#{@environment['cas.proxy-callback-url'] ?: null }")
    private String proxyCallbackUrl;

    /**
     * Cas20ProxyReceivingTicketValidationFilter proxyReceptorUrl parameter.
     */
    @Value("#{@environment['cas.proxy-receptor-url'] ?: null }")
    private String proxyReceptorUrl;

    /**
     * ValidationType the CAS protocol validation type. Defaults to CAS3 if not explicitly set.
     */
    @Value("#{@environment['cas.validation_type'] ?: null }")
    private ValidationType validationType = ValidationType.CAS3;

    /**
     * cas登出地址
     */
    @Value("#{@environment['cas.server-logout-url'] ?: null }")
    private String serverLogoutUrl;

    /**
     * cas登出后重定向地址
     */
    @Value("#{@environment['cas.logout-redirect-url'] ?: null }")
    private String logoutRedirectUrl;

    /**
     * true,使用单点登出功能
     */
    @Value("#{@environment['cas.use-single-sign-out'] ?: null }")
    private Boolean useSingleSignOut;
}
