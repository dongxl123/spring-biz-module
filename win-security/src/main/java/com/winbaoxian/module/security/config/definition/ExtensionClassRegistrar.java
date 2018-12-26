package com.winbaoxian.module.security.config.definition;

import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import com.winbaoxian.module.security.config.loader.WinSecurityClassLoaderConfiguration;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-04 17:37
 */
public class ExtensionClassRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(ExtensionClassRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributesMap = importingClassMetadata
                .getAnnotationAttributes(EnableWinSecurity.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributesMap);
        //注册WinSecurityClassLoaderConfiguration
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(WinSecurityClassLoaderConfiguration.class)
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_DTO.getValue(), annotationAttributes.getOrDefault(EnableWinSecurityAttributeEnum.EXTENSION_USER_DTO.getValue(), WinSecurityBaseUserDTO.class))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_ENTITY.getValue(), annotationAttributes.getOrDefault(EnableWinSecurityAttributeEnum.EXTENSION_USER_ENTITY.getValue(), WinSecurityBaseUserEntity.class))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_DTO.getValue(), annotationAttributes.getOrDefault(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_DTO.getValue(), WinSecurityBaseRoleDTO.class))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_ENTITY.getValue(), annotationAttributes.getOrDefault(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_ENTITY.getValue(), WinSecurityBaseRoleEntity.class))
                .getBeanDefinition();
        registry.registerBeanDefinition(WinSecurityClassLoaderConfiguration.class.getName(),
                beanDefinition);
        log.info("WinSecurity: ExtensionClassRegistrar, WinSecurityClassLoaderConfiguration.userDTOClass[{}] is setted", beanDefinition.getPropertyValues().get(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_DTO.getValue()));
        log.info("WinSecurity: ExtensionClassRegistrar, WinSecurityClassLoaderConfiguration.userEntityClass[{}] is setted", beanDefinition.getPropertyValues().get(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_ENTITY.getValue()));
        log.info("WinSecurity: ExtensionClassRegistrar, WinSecurityClassLoaderConfiguration.roleDTOClass[{}] is setted", beanDefinition.getPropertyValues().get(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_DTO.getValue()));
        log.info("WinSecurity: ExtensionClassRegistrar, WinSecurityClassLoaderConfiguration.roleEntityClass[{}] is setted", beanDefinition.getPropertyValues().get(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_ENTITY.getValue()));
        log.info("WinSecurity: ExtensionClassRegistrar, (Bean)[{}] is registered", WinSecurityClassLoaderConfiguration.class);
        //注册 serviceProcessorClass
        Class<?>[] serviceProcessorClassArray = annotationAttributes.getClassArray(EnableWinSecurityAttributeEnum.EXTENSION_SERVICE_PROCESSORS.getValue());
        if (ArrayUtils.isNotEmpty(serviceProcessorClassArray)) {
            for (Class serviceProcessorCls : serviceProcessorClassArray) {
                registry.registerBeanDefinition(serviceProcessorCls.getName(), BeanDefinitionBuilder.genericBeanDefinition(serviceProcessorCls).getBeanDefinition());
                log.info("WinSecurity: ExtensionClassRegistrar, (Bean)[{}] is registered", serviceProcessorCls);
            }
        }
        //注册 serviceFillerClass
        Class<?>[] serviceFillerClassArray = annotationAttributes.getClassArray(EnableWinSecurityAttributeEnum.EXTENSION_SERVICE_FILLERS.getValue());
        if (ArrayUtils.isNotEmpty(serviceFillerClassArray)) {
            for (Class serviceFillerCls : serviceFillerClassArray) {
                registry.registerBeanDefinition(serviceFillerCls.getName(), BeanDefinitionBuilder.genericBeanDefinition(serviceFillerCls).getBeanDefinition());
                log.info("WinSecurity: ExtensionClassRegistrar, (Bean)[{}] is registered", serviceFillerCls);
            }
        }
    }
}
