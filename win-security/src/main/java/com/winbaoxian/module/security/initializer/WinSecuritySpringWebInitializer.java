package com.winbaoxian.module.security.initializer;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author dongxuanliang252
 * @date 2019-04-11 18:45
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WinSecuritySpringWebInitializer {

    private Logger log = LoggerFactory.getLogger(getClass());

    WinSecuritySpringWebInitializer(WebApplicationContext context) {
        ServletContext servletContext = context.getServletContext();
        if (!(context instanceof XmlWebApplicationContext)) {
            return;
        }
        ShiroFilterFactoryBean shiroFilterFactoryBean = context.getBean(ShiroFilterFactoryBean.class);
        if (shiroFilterFactoryBean != null) {
            if (servletContext.getFilterRegistration(WinSecurityConstant.BEAN_NAME_SHIRO_FILTER) == null) {
                try {
                    DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
                    filterProxy.setTargetBeanName(WinSecurityConstant.BEAN_NAME_SHIRO_FILTER);
                    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
                    filterRegistrationBean.setName(WinSecurityConstant.BEAN_NAME_SHIRO_FILTER);
                    filterRegistrationBean.setFilter(filterProxy);
                    filterRegistrationBean.setOrder(10);
                    filterRegistrationBean.setMatchAfter(true);
                    filterRegistrationBean.addUrlPatterns("/*");
                    filterRegistrationBean.onStartup(servletContext);
                } catch (ServletException e) {
                    log.error("add Filter error, {}", WinSecurityConstant.BEAN_NAME_SHIRO_FILTER, e);
                }
            }
        }

    }


}
