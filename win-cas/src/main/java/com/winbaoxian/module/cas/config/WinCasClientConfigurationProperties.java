package com.winbaoxian.module.cas.config;

import com.winbaoxian.module.cas.enums.ValidationType;
import lombok.Getter;
import lombok.Setter;
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
    private String serverUrlPrefix;

    /**
     * CAS server login URL E.g. https://example.com/cas/login or https://cas.example/login. Required.
     */
    private String serverLoginUrl;

    /**
     * CAS-protected client application host URL E.g. https://myclient.example.com Required.
     */
    private String clientHostUrl;

    /**
     * List of URL patterns protected by CAS authentication filter.
     */
    private List<String> authenticationUrlPatterns = new ArrayList<>();

    /**
     * List of URL patterns protected by CAS validation filter.
     */
    private List<String> validationUrlPatterns = new ArrayList<>();

    /**
     * List of URL patterns protected by CAS request wrapper filter.
     */
    private List<String> requestWrapperUrlPatterns = new ArrayList<>();

    /**
     * Authentication filter gateway parameter.
     */
    private Boolean gateway;

    /**
     * Validation filter useSession parameter.
     */
    private Boolean useSession;

    /**
     * Validation filter redirectAfterValidation.
     */
    private Boolean redirectAfterValidation;

    /**
     * Cas20ProxyReceivingTicketValidationFilter acceptAnyProxy parameter.
     */
    private Boolean acceptAnyProxy;

    /**
     * Cas20ProxyReceivingTicketValidationFilter allowedProxyChains parameter.
     */
    private List<String> allowedProxyChains = new ArrayList<>();

    /**
     * Cas20ProxyReceivingTicketValidationFilter proxyCallbackUrl parameter.
     */
    private String proxyCallbackUrl;

    /**
     * Cas20ProxyReceivingTicketValidationFilter proxyReceptorUrl parameter.
     */
    private String proxyReceptorUrl;

    /**
     * ValidationType the CAS protocol validation type. Defaults to CAS3 if not explicitly set.
     */
    private ValidationType validationType = ValidationType.CAS3;

    /**
     * cas登出地址
     */
    private String serverLogoutUrl;

    /**
     * cas登出后重定向地址
     */
    private String logoutRedirectUrl;

    /**
     * true,使用单点登出功能
     */
    private Boolean useSingleSignOut;
}
