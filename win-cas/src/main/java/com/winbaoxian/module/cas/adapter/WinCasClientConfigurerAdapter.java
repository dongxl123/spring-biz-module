package com.winbaoxian.module.cas.adapter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * An implementation of {@link WinCasClientConfigurer} with empty methods allowing
 * sub-classes to override only the methods they're interested in.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 * @see WinCasClientConfigurer
 */
public class WinCasClientConfigurerAdapter implements WinCasClientConfigurer {

    @Override
    public void configureSingleSignOutFilter(FilterRegistrationBean singleSignOutFilter) {
        //Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        //Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

    @Override
    public void configureValidationFilter(FilterRegistrationBean validationFilter) {
        //Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

    @Override
    public void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter) {
        //Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

}
