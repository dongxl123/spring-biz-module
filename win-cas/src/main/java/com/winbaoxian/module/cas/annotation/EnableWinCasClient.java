package com.winbaoxian.module.cas.annotation;

import com.winbaoxian.module.cas.adapter.CasClientConfigurer;
import com.winbaoxian.module.cas.adapter.CasClientConfigurerAdapter;
import com.winbaoxian.module.cas.config.CasClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables CAS Java client Servlet Filters config facility.
 * To be used together with {@link org.springframework.context.annotation.Configuration Configuration}
 * or {@link org.springframework.boot.autoconfigure.SpringBootApplication SpringBootApplication} classes.
 *
 * <p>For those wishing to customize CAS filters during their creation, application config classes carrying this annotation
 * may implement the {@link CasClientConfigurer} callback interface or simply
 * extend the {@link CasClientConfigurerAdapter} and override only necessary methods.
 *
 * @author Dmitriy Kopylenko
 * @see CasClientConfigurer
 * @see CasClientConfiguration
 * @see CasClientConfigurerAdapter
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CasClientConfiguration.class)
public @interface EnableWinCasClient {

}
