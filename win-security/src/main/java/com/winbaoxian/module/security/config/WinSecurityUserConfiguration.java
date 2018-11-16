package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class WinSecurityUserConfiguration {

    @Value("${security.class.userDTO:WinSecurityBaseUserDTO}")
    private String userDTOClassName;
    @Value("${security.class.userEntity:WinSecurityBaseUserEntity}")
    private String userEntityClassName;

    private Class<? extends WinSecurityBaseUserDTO> userDTOClass;
    private Class<? extends WinSecurityBaseUserEntity> userEntityClass;

    @PostConstruct
    public void init() {
        try {
            userDTOClass = (Class<? extends WinSecurityBaseUserDTO>) ClassUtils.forName(userDTOClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.userDTO not set, prepare to use default class");
            userDTOClass = WinSecurityBaseUserDTO.class;
        }
        log.info("WinSecurity: UserDTOClass loaded, {}", userDTOClass.getName());

        try {
            userEntityClass = (Class<? extends WinSecurityBaseUserEntity>) ClassUtils.forName(userEntityClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.userEntity not set, prepare to use default class");
            userEntityClass = WinSecurityBaseUserEntity.class;
        }
        log.info("WinSecurity: UserEntityClass loaded, {}", userEntityClass.getName());
    }

    public Class<? extends WinSecurityBaseUserDTO> getUserDTOClass() {
        return userDTOClass;
    }

    public Class<? extends WinSecurityBaseUserEntity> getUserEntityClass() {
        return userEntityClass;
    }

}
