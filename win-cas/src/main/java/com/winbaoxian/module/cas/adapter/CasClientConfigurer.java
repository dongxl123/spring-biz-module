package com.winbaoxian.module.cas.adapter;

import com.winbaoxian.module.cas.annotation.EnableCasClient;
import com.winbaoxian.module.cas.config.CasClientConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * Callback interface to be implemented by {@link org.springframework.context.annotation.Configuration Configuration} classes annotated with
 * {@link EnableCasClient} that wish or need to
 * explicitly configure or customize CAS client filters created by {@link CasClientConfiguration
 * CasClientConfiguration}.
 *
 * Consider extending {@link CasClientConfigurerAdapter},
 * which provides a noop stub implementation of all interface methods.
 *
 * @author Dmitriy Kopylenko
 * @see CasClientConfigurerAdapter
 * @since 1.0.0
 */
public interface CasClientConfigurer {

    /**
     * Configure or customize CAS authentication filter.
     *
     * @param authenticationFilter
     */
    void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter);

    /**
     * Configure or customize CAS validation filter.
     *
     * @param validationFilter
     */
    void configureValidationFilter(FilterRegistrationBean validationFilter);

    /**
     * Configure or customize CAS http servlet wrapper filter.
     *
     * @param httpServletRequestWrapperFilter
     */
    void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter);

//    /**
//     * Configure or customize CAS assertion thread local filter.
//     *
//     * @param assertionThreadLocalFilter
//     */
//    void configureAssertionThreadLocalFilter(FilterRegistrationBean assertionThreadLocalFilter);
}
