package com.winbaoxian.module.config;

import com.winbaoxian.module.model.dto.BaseUserDTO;
import com.winbaoxian.module.model.entity.BaseUserEntity;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class UserConfiguration {

    @Value("${security.class.userDTO:com.winbaoxian.module.model.dto.BaseUserDTO}")
    private String userDTOClassName;
    @Value("${security.class.userEntity:com.winbaoxian.module.model.entity.BaseUserEntity}")
    private String userEntityClassName;

    private Class<? extends BaseUserDTO> userDTOClass;
    private Class<? extends BaseUserEntity> userEntityClass;

    @PostConstruct
    public void init() {
        try {
            userDTOClass = (Class<? extends BaseUserDTO>) ClassUtils.forName(userDTOClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.userDTO not set, prepare to use default class");
            userDTOClass = BaseUserDTO.class;
        }
        log.info("WinSecurity: UserDTOClass loaded, {}", userDTOClass.getName());

        try {
            userEntityClass = (Class<? extends BaseUserEntity>) ClassUtils.forName(userEntityClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.userEntity not set, prepare to use default class");
            userEntityClass = BaseUserEntity.class;
        }
        log.info("WinSecurity: UserEntityClass loaded, {}", userEntityClass.getName());
    }

    public Class<? extends BaseUserDTO> getUserDTOClass() {
        return userDTOClass;
    }

    public Class<? extends BaseUserEntity> getUserEntityClass() {
        return userEntityClass;
    }

}
