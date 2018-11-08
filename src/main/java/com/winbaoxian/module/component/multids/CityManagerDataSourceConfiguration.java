package com.winbaoxian.module.component.multids;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author DongXL
 * @Create 2018-01-09 21:36
 */

@Configuration
@EnableJpaRepositories(
        basePackages = "com.winbaoxian.broker.repository.citymanager",
        entityManagerFactoryRef = "entityManagerFactoryCitymanager",
        transactionManagerRef = "transactionManagerCitymanager")
public class CityManagerDataSourceConfiguration {

    @Resource
    private JpaProperties jpaProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.citymanager")
    public DataSource dataSourceCitymanager() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryCitymanager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSourceCitymanager())
                .packages(new String[]{"com.winbaoxian.broker.model.entity.citymanager","com.winbaoxian.cityManager.model"})
                .properties(jpaProperties.getHibernateProperties(dataSourceCitymanager()))
                .persistenceUnit("citymanager")
                .build();
    }

    @Bean
    @Primary
    PlatformTransactionManager transactionManagerCitymanager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryCitymanager(builder).getObject());
    }

}
