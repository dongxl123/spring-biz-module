package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author dongxuanliang252
 * @date 2018-12-05 17:37
 */
public class EntityManagerFactoryAttributesPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(EntityManagerFactoryAttributesPostProcessor.class);

    private static final String INTERNAL_PERSISTENCE_UNIT_MANAGER = "internalPersistenceUnitManager";
    private static final String PACKAGES_TO_SCAN = "packagesToScan";
    private static final String JPA_PROPERTY_MAP = "jpaPropertyMap";
    private static final String JPA_PROPERTY_MAP_PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy";
    private AnnotationAttributes enableWinSecurity;

    public EntityManagerFactoryAttributesPostProcessor() {
        this.enableWinSecurity = AnnotationAttributesHolder.INSTANCE.getEnableWinSecurity();
        Assert.notNull(this.enableWinSecurity, "EntityManagerFactoryAttributesPostProcessor.PostProcessor, can not found enableWinSecurity annotation attributes");
    }

    private boolean matchedBeanName(String beanName) {
        String entityManagerFactoryRef = this.enableWinSecurity.getString(EnableWinSecurityAttributeEnum.ENTITY_MANAGER_FACTORY_REF.getValue());
        return beanName.equals(entityManagerFactoryRef);
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalContainerEntityManagerFactoryBean && matchedBeanName(beanName)) {
            pvs = new MutablePropertyValues();
            for (PropertyDescriptor pd : pds) {
                if (PACKAGES_TO_SCAN.equals(pd.getName())) {
                    //apply @EnableWinSecurity entityClass packages and defaultEntityPackages:ENTITY_PACKAGES
                    String[] allEntityScanPackages = WinSecurityConstant.ENTITY_PACKAGES;
                    if (ArrayUtils.isNotEmpty(allEntityScanPackages)) {
                        String[] basePackages = getPackages(bean, pd.getName());
                        if (ArrayUtils.isNotEmpty(basePackages)) {
                            allEntityScanPackages = ArrayUtils.addAll(allEntityScanPackages, basePackages);
                        }
                        log.info("WinSecurity: EntityManagerFactoryAttributesPostProcessor, beanName:{}, propertyName:{}, change propertyValue to:{}", beanName, pd.getName(), allEntityScanPackages);
                        ((MutablePropertyValues) pvs).add(pd.getName(), allEntityScanPackages);
                    }
                }
            }
            return pvs;
        }
        return pvs;
    }

    private String[] getPackages(Object bean, String fieldName) {
        DefaultPersistenceUnitManager persistenceUnitManager = getBeanProperty(bean, INTERNAL_PERSISTENCE_UNIT_MANAGER);
        if (persistenceUnitManager != null) {
            return getBeanProperty(persistenceUnitManager, fieldName);
        }
        return null;
    }

    private <T> T getBeanProperty(Object bean, String fieldName) {
        try {
            Field field = ReflectionUtils.findField(bean.getClass(), fieldName);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return (T) field.get(bean);
        } catch (IllegalAccessException e) {
            log.error("bean reflect getBeanProperty failed, class:{}, fieldName:{} ", bean.getClass(), fieldName, e);
        }
        return null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.addBeanPostProcessor(this);
    }
}

