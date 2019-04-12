package com.winbaoxian.module.example.component.security;

import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author dongxuanliang252
 * @date 2019-04-12 13:46
 */
@Configuration
@EnableWinSecurity(transactionManagerRef = "transactionManagerWinSecurity", entityManagerFactoryRef = "entityManagerFactoryWinSecurity", tablePrefix = "security")
public class WinSecurityConfiguration {

    @Resource
    private DataSource dataSource;
    @Resource
    private SessionFactoryImpl sessionFactory;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryWinSecurity() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(new String[]{});
        factoryBean.setPersistenceUnitName("winSecurity");
        factoryBean.setJpaProperties(sessionFactory.getProperties());
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return factoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManagerWinSecurity() {
        return new JpaTransactionManager(entityManagerFactoryWinSecurity().getObject());
    }
}

