package com.winbaoxian.module.cas.initializer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author dongxuanliang252
 * @date 2019-04-11 18:45
 */
public class WinCasServletInitializer implements   BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

     public void onStartup(ServletContext servletContext) throws ServletException {
        Iterator var4 = this.getServletContextInitializerBeans().iterator();

        while (var4.hasNext()) {
            ServletContextInitializer beans = (ServletContextInitializer) var4.next();
            beans.onStartup(servletContext);
        }

        System.out.printf("111111");
    }

    protected Collection<ServletContextInitializer> getServletContextInitializerBeans() {
        return new ServletContextInitializerBeans(getBeanFactory());
    }


    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        }
    }
}
