package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.strategy.AbstractSecurityPhysicalNamingStrategy;
import com.winbaoxian.module.security.strategy.AbstractSecurityPhysicalNamingStrategyStandardImpl;
import com.winbaoxian.module.security.strategy.DefaultSecurityPhysicalNamingStrategyStandardImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-05 17:37
 */
@EnableAspectJAutoProxy
public class RepositoryAttributesPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(RepositoryAttributesPostProcessor.class);

    private static final String INTERNAL_PERSISTENCE_UNIT_MANAGER = "internalPersistenceUnitManager";
    private static final String PACKAGES_TO_SCAN = "packagesToScan";
    private static final String JPA_PROPERTY_MAP = "jpaPropertyMap";
    private static final String JPA_PROPERTY_MAP_PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy";
    private AnnotationAttributes enableWinSecurity;

    public RepositoryAttributesPostProcessor() {
        this.enableWinSecurity = AnnotationAttributesHolder.INSTANCE.getEnableWinSecurity();
        Assert.notNull(this.enableWinSecurity, "RepositoryAttributesConfiguration.PostProcessor, can not found enableWinSecurity annotation attributes");
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
                    //apply @EnableWinSecurity entityScanPackages and defaultEntityPackages:ENTITY_PACKAGES
                    String[] allEntityScanPackages = WinSecurityConstant.ENTITY_PACKAGES;
                    String[] annotationEntityScanPackages = enableWinSecurity.getStringArray(EnableWinSecurityAttributeEnum.ENTITY_SCAN_PACKAGES.getValue());
                    if (ArrayUtils.isNotEmpty(annotationEntityScanPackages)) {
                        allEntityScanPackages = ArrayUtils.addAll(allEntityScanPackages, annotationEntityScanPackages);
                    }
                    if (ArrayUtils.isNotEmpty(allEntityScanPackages)) {
                        String[] basePackages = getPackages(bean, pd.getName());
                        if (ArrayUtils.isNotEmpty(basePackages)) {
                            allEntityScanPackages = ArrayUtils.addAll(allEntityScanPackages, basePackages);
                        }
                        ((MutablePropertyValues) pvs).add(pd.getName(), allEntityScanPackages);
                    }
                } else if (JPA_PROPERTY_MAP.equals(pd.getName())) {
                    //apply @EnableWinSecurity tablePrefix
                    String tablePrefix = enableWinSecurity.getString(EnableWinSecurityAttributeEnum.TABLE_PREFIX.getValue());
                    if (StringUtils.isNotBlank(tablePrefix)) {
                        Map jpaPropertyMap = getBeanProperty(bean, pd.getName());
                        if (MapUtils.isEmpty(jpaPropertyMap)) {
                            jpaPropertyMap = new HashMap<>();
                        }
                        boolean needChange = false;
                        if (jpaPropertyMap.containsKey(JPA_PROPERTY_MAP_PHYSICAL_NAMING_STRATEGY)) {
                            String physicalNamingStrategyClassName = MapUtils.getString(jpaPropertyMap, JPA_PROPERTY_MAP_PHYSICAL_NAMING_STRATEGY);
                            if (StringUtils.isBlank(physicalNamingStrategyClassName)) {
                                needChange = true;
                            } else {
                                try {
                                    Class cls = ClassUtils.forName(physicalNamingStrategyClassName, getClass().getClassLoader());
                                    if (!ClassUtils.isAssignable(AbstractSecurityPhysicalNamingStrategy.class, cls) && !ClassUtils.isAssignable(AbstractSecurityPhysicalNamingStrategyStandardImpl.class, cls)) {
                                        needChange = true;
                                    }
                                } catch (Exception e) {
                                    log.error("WinSecurity: class:{} load failed", physicalNamingStrategyClassName, e);
                                    needChange = true;
                                }
                            }
                        } else {
                            needChange = true;
                        }
                        if (needChange) {
                            String newPhysicalNamingStrategyClassName = DefaultSecurityPhysicalNamingStrategyStandardImpl.class.getName();
                            jpaPropertyMap.put(JPA_PROPERTY_MAP_PHYSICAL_NAMING_STRATEGY, newPhysicalNamingStrategyClassName);
                            ((MutablePropertyValues) pvs).add(JPA_PROPERTY_MAP, jpaPropertyMap);
                        }
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
