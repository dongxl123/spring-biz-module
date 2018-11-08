package com.winbaoxian.module.component.listener;

import com.winbaoxian.module.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SpringContextListener implements ServletContextListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpringContextHolder.INSTANCE.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }


}


