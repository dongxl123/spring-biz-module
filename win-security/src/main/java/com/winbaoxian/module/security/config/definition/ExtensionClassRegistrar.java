package com.winbaoxian.module.security.config.definition;

import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        //注册 serviceProcessorClass
        Class<?>[] serviceProcessorClassArray = annotationAttributes.getClassArray(EnableWinSecurityAttributeEnum.EXTENSION_SERVICE_PROCESSORS.getValue());
        if (ArrayUtils.isNotEmpty(serviceProcessorClassArray)) {
            for (Class serviceProcessorCls : serviceProcessorClassArray) {
                registry.registerBeanDefinition(serviceProcessorCls.getName(), BeanDefinitionBuilder.genericBeanDefinition(serviceProcessorCls).getBeanDefinition());
                log.info("WinSecurity: ExtensionClassRegistrar, (Bean)[{}] is registered", serviceProcessorCls);
            }
        }
    }
}
