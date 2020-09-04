package com.winbaoxian.module.security.annotation;

import com.winbaoxian.module.security.config.ControllerEffectiveScope;
import com.winbaoxian.module.security.config.EntityManagerFactoryAttributesPostProcessor;
import com.winbaoxian.module.security.config.definition.AnnotationAttributesRegistrar;
import com.winbaoxian.module.security.config.definition.ControllerEffectiveScopeSelector;
import com.winbaoxian.module.security.config.definition.ExtensionClassRegistrar;
import com.winbaoxian.module.security.config.definition.SysLogSelector;
import com.winbaoxian.module.security.config.exception.WinSecurityExceptionHandler;
import com.winbaoxian.module.security.config.shiro.ShiroConfiguration;
import com.winbaoxian.module.security.config.shiro.ShiroFilterConfiguration;
import com.winbaoxian.module.security.config.shiro.WinSecurityErrorController;
import com.winbaoxian.module.security.config.transaction.WinSecurityTransactionConfiguration;
import com.winbaoxian.module.security.initializer.WinSecuritySpringWebInitializer;
import com.winbaoxian.module.security.service.*;
import com.winbaoxian.module.security.service.extension.IProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
@Inherited
@ImportAutoConfiguration({WinSecurityTransactionConfiguration.class,
        WinSecurityExceptionHandler.class,
        ShiroConfiguration.class,
        ShiroFilterConfiguration.class,
        WinSecurityAccessService.class,
        WinSecurityUserService.class,
        WinSecurityRoleService.class,
        WinSecurityResourceService.class,
        WinSecuritySysLogService.class,
        WinSecuritySpringWebInitializer.class
})
@Import({ExtensionClassRegistrar.class,
        SysLogSelector.class,
        ControllerEffectiveScopeSelector.class,
        WinSecurityErrorController.class,
        AnnotationAttributesRegistrar.class,
        EntityManagerFactoryAttributesPostProcessor.class})
@EnableJpaRepositories(basePackages = "com.winbaoxian.module.security.repository")
public @interface EnableWinSecurity {
    /**
     * winSecurity 应用编码
     */
    String appCode() default "";

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
     * controller层代码生效范围
     *
     * @see ControllerEffectiveScope
     */
    ControllerEffectiveScope[] controllerScopes() default ControllerEffectiveScope.ALL;

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

    /**
     * excludePathPatterns
     */
    String[] excludePathPatterns() default {"/**"};

}





