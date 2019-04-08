package com.winbaoxian.module.cas;

import com.winbaoxian.module.cas.annotation.EnableCasClient;
import com.winbaoxian.module.cas.adapter.CasClientConfigurerAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongxuanliang252
 * @date 2019-04-04 13:49
 */
@Configuration
@EnableCasClient
public class CasConfigure extends CasClientConfigurerAdapter {

    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        super.configureAuthenticationFilter(authenticationFilter);
        authenticationFilter.getInitParameters().put("authenticationRedirectStrategyClass","com.winbaoxian.module.cas.AjaxAuthRedirectStrategy");
    }

    @Override
    public void configureValidationFilter(FilterRegistrationBean validationFilter) {
        System.out.println(11);
        //Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

    @Override
    public void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter) {
        System.out.println(22);
//Noop. Designed to be overridden if necessary to ease plugging in custom configs.
    }

}
