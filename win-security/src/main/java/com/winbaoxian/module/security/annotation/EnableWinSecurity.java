package com.winbaoxian.module.security.annotation;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 11:36
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan({"com.winbaoxian.module.security.config.shiro"})
@ComponentScan({"com.winbaoxian.module.security.config.loader"})
@ComponentScan({"com.winbaoxian.module.security.controller"})
@ComponentScan({"com.winbaoxian.module.security.service"})
@ComponentScan({"com.winbaoxian.module.security.config.exception"})
//@Import({WinSecurityRegistrar.class})
//@EntityScan({"com.winbaoxian.module.security.model.entity"})
@ServletComponentScan
public @interface EnableWinSecurity {

    String entityManagerFactoryRef() default "entityManagerFactory";

    String transactionManagerRef() default "transactionManager";

    String[] extensionEntityPackages() default {};
}

