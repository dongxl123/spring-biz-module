package com.winbaoxian.module.security.annotation;

import com.winbaoxian.module.security.config.ControllerEffectiveScope;
import com.winbaoxian.module.security.config.ControllerEffectiveScopeSelector;
import com.winbaoxian.module.security.config.ExtensionClassRegistrar;
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
@ComponentScan({"com.winbaoxian.module.security.config.exception"})
@ComponentScan({"com.winbaoxian.module.security.service"})
@Import({ExtensionClassRegistrar.class, ControllerEffectiveScopeSelector.class})
//@EntityScan({"com.winbaoxian.module.security.model.entity"})
@ServletComponentScan
public @interface EnableWinSecurity {

    String entityManagerFactoryRef() default "entityManagerFactory";

    String transactionManagerRef() default "transactionManager";

    String[] entityScanPackages() default {};

    ControllerEffectiveScope[] controllerScopes() default ControllerEffectiveScope.ALL;

    /**
     * subclass of {@link WinSecurityBaseUserDTO}
     *
     * @return
     */
    Class<?> extensionUserDTO() default WinSecurityBaseUserDTO.class;

    /**
     * subclass of {@link WinSecurityBaseUserEntity}
     *
     * @return
     */
    Class<?> extensionUserEntity() default WinSecurityBaseUserEntity.class;

    /**
     * subclass of {@link WinSecurityBaseRoleDTO}
     *
     * @return
     */
    Class<?> extensionRoleDTO() default WinSecurityBaseRoleDTO.class;

    /**
     * subclass of {@link WinSecurityBaseRoleEntity}
     *
     * @return
     */
    Class<?> extensionRoleEntity() default WinSecurityBaseRoleEntity.class;

    /**
     * implements of {@link com.winbaoxian.module.security.service.extension.IUserAddProcessor} or {@link com.winbaoxian.module.security.service.extension.IUserUpdateProcessor} or {@link com.winbaoxian.module.security.service.extension.IRoleAddProcessor} or {@link com.winbaoxian.module.security.service.extension.IRoleUpdateProcessor }
     *
     * @return
     */
    Class<?>[] extensionServiceProcessors() default {};

}




