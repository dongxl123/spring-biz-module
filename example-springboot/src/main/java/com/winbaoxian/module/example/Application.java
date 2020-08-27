package com.winbaoxian.module.example;

import com.winbaoxian.module.cas.annotation.EnableWinCasClient;
import com.winbaoxian.module.example.service.*;
import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ServletComponentScan 开启listner和filter
 * @EnableHystrix 开启熔断
 */
@SpringBootApplication
@EnableWinSecurity(
        entityManagerFactoryRef = "entityManagerFactoryCitymanager",
        transactionManagerRef = "transactionManagerCitymanager",
        extensionServiceProcessors = {RoleAddProcessorImpl.class, RoleUpdateProcessorImpl.class, UserAddProcessorImpl.class, UserUpdateProcessorImpl.class, UserPageProcessorImpl.class},
        sysLog = true)
//@EnableWinCasClient
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
