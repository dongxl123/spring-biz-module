package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.pac4j.WinCasCallbackLogic;
import com.winbaoxian.module.security.pac4j.WinCasClient;
import com.winbaoxian.module.security.pac4j.WinCasRequestResolver;
import io.buji.pac4j.context.ShiroSessionStore;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import lombok.Getter;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.engine.CallbackLogic;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class CasConfig {

    @Autowired
    private WinCasClientConfigurationProperties winCasClientConfigurationProperties;

    @Autowired
    private AjaxRequestResolver ajaxRequestResolver;

    @Bean
    public Config config() {
        final CasConfiguration configuration = casConfiguration();
        final CasClient casClient = new WinCasClient(configuration);
        casClient.setAjaxRequestResolver(new WinCasRequestResolver());
        final Clients clients = new Clients(winCasClientConfigurationProperties.getClientHostUrl()+winCasClientConfigurationProperties.getProxyCallbackUrl(), casClient);
        final Config config = new Config(clients);
        config.setSessionStore(new ShiroSessionStore());
        return config;
    }

    private CasConfiguration casConfiguration(){
        final CasConfiguration configuration = new CasConfiguration(winCasClientConfigurationProperties.getServerLoginUrl());
        return configuration;
    }


    @Bean
    @ConditionalOnMissingBean(AjaxRequestResolver.class)
    public AjaxRequestResolver ajaxRequestResolver(){
        return new WinCasRequestResolver();
    }

    public SecurityFilter mySecurityFilter(){
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config());
        return securityFilter;
    }

    public LogoutFilter logoutFilter(){
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config());
        logoutFilter.setCentralLogout(true);
        logoutFilter.setDefaultUrl(winCasClientConfigurationProperties.getLogoutRedirectUrl());
        logoutFilter.setCentralLogout(winCasClientConfigurationProperties.getUseSingleSignOut());
        return logoutFilter;
    }

    public CallbackFilter callbackFilter(){
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config());
        callbackFilter.setCallbackLogic(callbackLogic());
        return callbackFilter;
    }

    public CallbackLogic callbackLogic(){
        return new WinCasCallbackLogic();
    }



}
