package com.winbaoxian.module.example;

import com.winbaoxian.module.cas.annotation.EnableWinCasClient;
import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityRoleEntity;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.example.service.*;
import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @ServletComponentScan 开启listner和filter
 * @EnableHystrix 开启熔断
 */
@SpringBootApplication
@EnableWinSecurity(
        entityManagerFactoryRef = "entityManagerFactoryCitymanager",
        transactionManagerRef = "transactionManagerCitymanager",
        tablePrefix = "SECURITY",
        extensionUserDTO = SecurityUserDTO.class,
        extensionUserEntity = SecurityUserEntity.class,
        extensionRoleDTO = SecurityRoleDTO.class,
        extensionRoleEntity = SecurityRoleEntity.class,
        extensionServiceProcessors = {RoleAddProcessorImpl.class, RoleUpdateProcessorImpl.class, UserAddProcessorImpl.class, UserUpdateProcessorImpl.class, UserPageProcessorImpl.class},
        extensionServiceFillers = {UserFillerImpl.class},
        sysLog = true)
@EnableWinCasClient
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
