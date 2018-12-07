package com.winbaoxian.module.example.component.multids;

import com.winbaoxian.vault.VaultTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = {"com.winbaoxian.module.example.repository.tob"},
        entityManagerFactoryRef = "entityManagerFactoryTob",
        transactionManagerRef = "transactionManagerTob")
public class TobDataSourceConfiguration {

    @Resource
    private JpaProperties jpaProperties;
    @Resource
    private VaultTools vaultTools;
    @Value("${spring.datasource.tob.cipherText}")
    private String cipherText;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.tob")
    public DataSource dataSourceTob() {
        String password = vaultTools.decrypt(cipherText);
        return DataSourceBuilder.create().password(password).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryTob(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSourceTob())
                .packages(new String[]{"com.winbaoxian.module.example.model.entity.tob"})
                .properties(jpaProperties.getHibernateProperties(dataSourceTob()))
                .persistenceUnit("tob")
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManagerTob(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryTob(builder).getObject());
    }

}
