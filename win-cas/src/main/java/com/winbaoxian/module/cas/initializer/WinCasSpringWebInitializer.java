package com.winbaoxian.module.cas.initializer;

import com.winbaoxian.module.cas.filter.WinCasLogoutFilter;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.OrderComparator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.*;

/**
 * @author dongxuanliang252
 * @date 2019-04-11 18:45
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WinCasSpringWebInitializer {

    private Logger log = LoggerFactory.getLogger(getClass());
    private List<Class> CAS_FILTER_CLASS_LIST = Arrays.asList(SingleSignOutFilter.class, AuthenticationFilter.class, Cas20ProxyReceivingTicketValidationFilter.class, Cas30ProxyReceivingTicketValidationFilter.class, HttpServletRequestWrapperFilter.class, WinCasLogoutFilter.class);
    private Class CAS_LISTENER_CLASS = SingleSignOutHttpSessionListener.class;

    WinCasSpringWebInitializer(WebApplicationContext context) {
        ServletContext servletContext = context.getServletContext();
        if (!(context instanceof XmlWebApplicationContext)) {
            return;
        }
        // cas filter
        Map<String, FilterRegistrationBean> filterRegistrationBeanMap = context.getBeansOfType(FilterRegistrationBean.class);
        if (MapUtils.isNotEmpty(filterRegistrationBeanMap)) {
            List<FilterRegistrationBean> filterRegistrationBeanList = new ArrayList<>(filterRegistrationBeanMap.values());
            OrderComparator.sort(filterRegistrationBeanList);
            for (FilterRegistrationBean filterRegistrationBean : filterRegistrationBeanList) {
                Filter filter = filterRegistrationBean.getFilter();
                if (isCasFilter(filter) && !existFilter(servletContext, filter)) {
                    try {
                        filterRegistrationBean.onStartup(servletContext);
                    } catch (ServletException e) {
                        log.error("cas add Filter error, {}", getBeanName(filter.getClass()), e);
                    }
                }
            }
        }
        //ca listener
        Map<String, ServletListenerRegistrationBean> listenerRegistrationBeanMap = context.getBeansOfType(ServletListenerRegistrationBean.class);
        if (MapUtils.isNotEmpty(listenerRegistrationBeanMap)) {
            for (ServletListenerRegistrationBean listenerRegistrationBean : listenerRegistrationBeanMap.values()) {
                EventListener listener = listenerRegistrationBean.getListener();
                if (isCasListener(listener)) {
                    try {
                        listenerRegistrationBean.onStartup(servletContext);
                    } catch (ServletException e) {
                        log.error("cas add Listener error, {}", getBeanName(listener.getClass()), e);
                    }
                }
            }
        }
    }

    private boolean isCasFilter(Filter filter) {
        return CAS_FILTER_CLASS_LIST.contains(filter.getClass());
    }

    private boolean existFilter(ServletContext servletContext, Filter filter) {
        String filterName = getBeanName(filter.getClass());
        FilterRegistration filterRegistration = servletContext.getFilterRegistration(filterName);
        if (filterRegistration == null) {
            return false;
        }
        return true;
    }


    private boolean isCasListener(EventListener listener) {
        return CAS_LISTENER_CLASS.equals(listener.getClass());
    }

    private String getBeanName(Class cls) {
        String shortName = ClassUtils.getShortCanonicalName(cls);
        if (StringUtils.isBlank(shortName)) {
            throw new BeanCreationException("getBeanName error");
        }
        return shortName.substring(0, 1).toLowerCase() + shortName.substring(1);
    }

}
