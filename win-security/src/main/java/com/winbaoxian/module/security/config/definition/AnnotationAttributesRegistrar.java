package com.winbaoxian.module.security.config.definition;

import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.AnnotationAttributesHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-07 10:37
 */
public class AnnotationAttributesRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(AnnotationAttributesRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributesMap = importMetadata
                .getAnnotationAttributes(EnableWinSecurity.class.getName());
        AnnotationAttributes enableWinSecurity = AnnotationAttributes.fromMap(annotationAttributesMap);
        if (enableWinSecurity == null) {
            throw new IllegalArgumentException(
                    "@EnableWinSecurity is not present on importing class " + importMetadata.getClassName());
        }
        log.info("WinSecurity: AnnotationAttributesRegistrar, [{}] properties is setted ", AnnotationAttributesHolder.class);
        AnnotationAttributesHolder.INSTANCE.setEnableWinSecurity(enableWinSecurity);
    }
}