package com.winbaoxian.module.security.initializer;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.OrderComparator;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<String, FilterRegistrationBean> filterRegistrationBeanMap = context.getBeansOfType(FilterRegistrationBean.class);
        if (MapUtils.isNotEmpty(filterRegistrationBeanMap)) {
            List<FilterRegistrationBean> filterRegistrationBeanList = new ArrayList<>(filterRegistrationBeanMap.values());
            OrderComparator.sort(filterRegistrationBeanList);
            for (FilterRegistrationBean filterRegistrationBean : filterRegistrationBeanList) {
                Filter filter = filterRegistrationBean.getFilter();
                if (isSecurityFilter(filter) && !existFilter(servletContext, WinSecurityConstant.BEAN_NAME_SHIRO_FILTER)) {
                    try {
                        filterRegistrationBean.onStartup(servletContext);
                    } catch (ServletException e) {
                        log.error("security add Filter error, {}", WinSecurityConstant.BEAN_NAME_SHIRO_FILTER, e);
                    }
                }
            }
        }
    }

    private boolean isSecurityFilter(Filter filter) {
        return (filter instanceof DelegatingFilterProxy) && WinSecurityConstant.BEAN_NAME_SHIRO_FILTER.equals(getFilterTargetBeanName((DelegatingFilterProxy) filter));
    }

    private boolean existFilter(ServletContext servletContext, String filterName) {
        FilterRegistration filterRegistration = servletContext.getFilterRegistration(filterName);
        if (filterRegistration == null) {
            return false;
        }
        return true;
    }

    private String getFilterTargetBeanName(DelegatingFilterProxy filterProxy) {
        String fieldName = "targetBeanName";
        try {
            Field field = ReflectionUtils.findField(filterProxy.getClass(), fieldName);
            field.setAccessible(true);
            return (String) field.get(filterProxy);
        } catch (IllegalAccessException e) {
            log.error("bean reflect getBeanProperty failed, class:{}, fieldName:{} ", filterProxy.getClass(), fieldName, e);
        }
        return null;
    }

}
