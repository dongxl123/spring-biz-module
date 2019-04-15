package com.winbaoxian.module.cas.initializer;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @author dongxuanliang252
 * @date 2019-04-11 18:45
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(WebMvcProperties.class)
public class WinCasSpringWebInitializerConfiguration {

    private final WebApplicationContext context;


    WinCasSpringWebInitializerConfiguration(WebApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    private void test() {
        System.out.println(11111);
    }

}
