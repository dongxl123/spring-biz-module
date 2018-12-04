package com.winbaoxian.module.example;

import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityRoleEntity;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.example.service.RoleAddProcessorImpl;
import com.winbaoxian.module.example.service.RoleUpdateProcessorImpl;
import com.winbaoxian.module.example.service.UserAddProcessorImpl;
import com.winbaoxian.module.example.service.UserUpdateProcessorImpl;
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
        extensionUserDTO = SecurityUserDTO.class,
        extensionUserEntity = SecurityUserEntity.class,
        extensionRoleDTO = SecurityRoleDTO.class,
        extensionRoleEntity = SecurityRoleEntity.class,
        extensionServiceProcessors = {RoleAddProcessorImpl.class, RoleUpdateProcessorImpl.class, UserAddProcessorImpl.class, UserUpdateProcessorImpl.class})
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
