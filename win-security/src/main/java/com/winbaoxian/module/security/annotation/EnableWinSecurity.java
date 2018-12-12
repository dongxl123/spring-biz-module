package com.winbaoxian.module.security.annotation;

import com.winbaoxian.module.security.config.ControllerEffectiveScope;
import com.winbaoxian.module.security.config.RepositoryAttributesPostProcessor;
import com.winbaoxian.module.security.config.definition.AnnotationAttributesRegistrar;
import com.winbaoxian.module.security.config.definition.ControllerEffectiveScopeSelector;
import com.winbaoxian.module.security.config.definition.ExtensionClassRegistrar;
import com.winbaoxian.module.security.config.definition.SysLogSelector;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import com.winbaoxian.module.security.service.extension.IProcessor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 11:36
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan({"com.winbaoxian.module.security.config.shiro"})
@ComponentScan({"com.winbaoxian.module.security.config.exception"})
@ComponentScan({"com.winbaoxian.module.security.config.log"})
@ComponentScan({"com.winbaoxian.module.security.service"})
@Import({ExtensionClassRegistrar.class,
        SysLogSelector.class,
        ControllerEffectiveScopeSelector.class,
        AnnotationAttributesRegistrar.class,
        RepositoryAttributesPostProcessor.class})
@EnableJpaRepositories(basePackages = "com.winbaoxian.module.security.repository")
@ServletComponentScan
public @interface EnableWinSecurity {

    /**
     * winSecurity entityManagerFactory
     */
    @AliasFor(annotation = EnableJpaRepositories.class)
    String entityManagerFactoryRef() default "entityManagerFactory";

    /**
     * winSecurity transactionManager
     */
    @AliasFor(annotation = EnableJpaRepositories.class)
    String transactionManagerRef() default "transactionManager";

    /**
     * 表前缀
     */
    String tablePrefix() default "";

    /**
     * controller层代码生效范围
     *
     * @see ControllerEffectiveScope
     */
    ControllerEffectiveScope[] controllerScopes() default ControllerEffectiveScope.ALL;

    /**
     * subclass of {@link WinSecurityBaseUserDTO}
     *
     * @return
     */
    Class<? extends WinSecurityBaseUserDTO> extensionUserDTO() default WinSecurityBaseUserDTO.class;

    /**
     * subclass of {@link WinSecurityBaseUserEntity}
     *
     * @return
     */
    Class<? extends WinSecurityBaseUserEntity> extensionUserEntity() default WinSecurityBaseUserEntity.class;

    /**
     * subclass of {@link WinSecurityBaseRoleDTO}
     *
     * @return
     */
    Class<? extends WinSecurityBaseRoleDTO> extensionRoleDTO() default WinSecurityBaseRoleDTO.class;

    /**
     * subclass of {@link WinSecurityBaseRoleEntity}
     *
     * @return
     */
    Class<? extends WinSecurityBaseRoleEntity> extensionRoleEntity() default WinSecurityBaseRoleEntity.class;

    /**
     * implements of {@link com.winbaoxian.module.security.service.extension.IUserAddProcessor} or {@link com.winbaoxian.module.security.service.extension.IUserUpdateProcessor} or {@link com.winbaoxian.module.security.service.extension.IRoleAddProcessor} or {@link com.winbaoxian.module.security.service.extension.IRoleUpdateProcessor }
     *
     * @return
     */
    Class<? extends IProcessor>[] extensionServiceProcessors() default {};

    /**
     * sysLog switch
     *
     * @return
     */
    boolean sysLog() default false;

}





