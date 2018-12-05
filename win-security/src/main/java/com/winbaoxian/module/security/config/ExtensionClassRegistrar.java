package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.loader.WinSecurityClassLoaderConfiguration;
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
        AnnotationAttributes annotationAttributes = new AnnotationAttributes(annotationAttributesMap);
        //注册WinSecurityClassLoaderConfiguration
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(WinSecurityClassLoaderConfiguration.class)
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_DTO.getValue(), annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_USER_DTO.getValue()))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_USER_ENTITY.getValue(), annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_USER_ENTITY.getValue()))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_DTO.getValue(), annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_DTO.getValue()))
                .addPropertyValue(WinSecurityClassLoaderConfiguration.ClassNameEnum.CLASS_EXTENSION_ROLE_ENTITY.getValue(), annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_ENTITY.getValue()))
                .getBeanDefinition();
        registry.registerBeanDefinition(WinSecurityClassLoaderConfiguration.class.getName(),
                beanDefinition);
        log.debug("WinSecurity: ExtensionClassRegistrar, class is set, {}", annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_USER_DTO.getValue()));
        log.debug("WinSecurity: ExtensionClassRegistrar, class is set, {}", annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_USER_ENTITY.getValue()));
        log.debug("WinSecurity: ExtensionClassRegistrar, class is set, {}", annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_DTO.getValue()));
        log.debug("WinSecurity: ExtensionClassRegistrar, class is set, {}", annotationAttributes.getClass(EnableWinSecurityAttributeEnum.EXTENSION_ROLE_ENTITY.getValue()));
        log.debug("WinSecurity: ExtensionClassRegistrar, beanDefinition is register, {}", WinSecurityClassLoaderConfiguration.class);
        //注册 serviceProcessorClass
        Class<?>[] serviceProcessorClassArray = annotationAttributes.getClassArray(EnableWinSecurityAttributeEnum.EXTENSION_SERVICE_PROCESSORS.getValue());
        if (ArrayUtils.isNotEmpty(serviceProcessorClassArray)) {
            for (Class serviceProcessorCls : serviceProcessorClassArray) {
                registry.registerBeanDefinition(serviceProcessorCls.getName(), BeanDefinitionBuilder.genericBeanDefinition(serviceProcessorCls).getBeanDefinition());
                log.debug("WinSecurity: ExtensionClassRegistrar, beanDefinition is register, {}", serviceProcessorCls);
            }
        }
    }
}
