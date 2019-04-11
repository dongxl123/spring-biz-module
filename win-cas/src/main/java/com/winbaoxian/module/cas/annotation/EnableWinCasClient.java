package com.winbaoxian.module.cas.annotation;

import com.winbaoxian.module.cas.adapter.WinCasClientConfigurer;
import com.winbaoxian.module.cas.adapter.WinCasClientConfigurerAdapter;
import com.winbaoxian.module.cas.config.WinCasClientConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * Enables CAS Java client Servlet Filters config facility.
 * To be used together with {@link org.springframework.context.annotation.Configuration Configuration}
 * or {@link org.springframework.boot.autoconfigure.SpringBootApplication SpringBootApplication} classes.
 *
 * <p>For those wishing to customize CAS filters during their creation, application config classes carrying this annotation
 * may implement the {@link WinCasClientConfigurer} callback interface or simply
 * extend the {@link WinCasClientConfigurerAdapter} and override only necessary methods.
 *
 * @author Dmitriy Kopylenko
 * @see WinCasClientConfigurer
 * @see WinCasClientConfiguration
 * @see WinCasClientConfigurerAdapter
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan({"com.winbaoxian.module.cas.config"})
@ComponentScan({"com.winbaoxian.module.cas.controller"})
public @interface EnableWinCasClient {

}
