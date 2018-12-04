package com.winbaoxian.module.security.annotation;

import com.winbaoxian.module.security.config.WinSecurityRegistrar;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

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
@Import({WinSecurityRegistrar.class})
//@EntityScan({"com.winbaoxian.module.security.model.entity"})
@ServletComponentScan
public @interface EnableWinSecurity {

    String entityManagerFactoryRef() default "entityManagerFactory";

    String transactionManagerRef() default "transactionManager";

    String[] extensionEntityPackages() default {};

    Class<?> extensionUserDTO() default WinSecurityBaseUserDTO.class;

    Class<?> extensionUserEntity() default WinSecurityBaseUserEntity.class;

    Class<?> extensionRoleDTO() default WinSecurityBaseRoleDTO.class;

    Class<?> extensionRoleEntity() default WinSecurityBaseRoleEntity.class;

    Class<?>[] extensionServiceProcessors() default {};

}

