package com.winbaoxian.module.security.config;

import org.springframework.core.annotation.AnnotationAttributes;

/**
 * can use after register beanDefinition
 *
 * @author dongxuanliang252
 * @date 2018-12-07 10:32
 */
public enum AnnotationAttributesHolder {
    INSTANCE;
    private AnnotationAttributes enableWinSecurity;

    public AnnotationAttributes getEnableWinSecurity() {
        return enableWinSecurity;
    }

    public void setEnableWinSecurity(AnnotationAttributes enableWinSecurity) {
        this.enableWinSecurity = enableWinSecurity;
    }
}
