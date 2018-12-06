package com.winbaoxian.module.security.annotation;

import com.winbaoxian.module.security.config.ControllerEffectiveScope;
import com.winbaoxian.module.security.config.ControllerEffectiveScopeSelector;
import com.winbaoxian.module.security.config.ExtensionClassRegistrar;
import com.winbaoxian.module.security.config.RepositoryAttributesConfiguration;
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
@ComponentScan({"com.winbaoxian.module.security.service"})
@Import({ExtensionClassRegistrar.class,
        ControllerEffectiveScopeSelector.class,
        RepositoryAttributesConfiguration.Registrar.class,
        RepositoryAttributesConfiguration.PostProcessor.class})
@EnableJpaRepositories(basePackages = "com.winbaoxian.module.security.repository")
@ServletComponentScan
public @interface EnableWinSecurity {

    @AliasFor(annotation = EnableJpaRepositories.class)
    String entityManagerFactoryRef() default "entityManagerFactory";

    @AliasFor(annotation = EnableJpaRepositories.class)
    String transactionManagerRef() default "transactionManager";

    String[] entityScanPackages() default {};

    String tablePrefix() default "";

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

}




