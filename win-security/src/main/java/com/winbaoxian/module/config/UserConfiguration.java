package com.winbaoxian.module.config;

import com.winbaoxian.module.model.dto.BaseUserDTO;
import com.winbaoxian.module.model.entity.BaseUserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;

@Configuration
public class UserConfiguration {


    @Value("${security.userClass.dto}")
    private String userDTOClassName;
    @Value("${security.userClass.entity}")
    private String userEntityClassName;

    private Class<? extends BaseUserDTO> userDTOClass;
    private Class<? extends BaseUserEntity> userEntityClass;

    @PostConstruct
    public void init() throws ClassNotFoundException {
        userDTOClass = (Class<? extends BaseUserDTO>) ClassUtils.forName(userDTOClassName, getClass().getClassLoader());
        userEntityClass = (Class<? extends BaseUserEntity>) ClassUtils.forName(userEntityClassName, getClass().getClassLoader());
    }

    public Class<? extends BaseUserDTO> getUserDTOClass() {
        return userDTOClass;
    }

    public void setUserDTOClass(Class<? extends BaseUserDTO> userDTOClass) {
        this.userDTOClass = userDTOClass;
    }

    public Class<? extends BaseUserEntity> getUserEntityClass() {
        return userEntityClass;
    }

    public void setUserEntityClass(Class<? extends BaseUserEntity> userEntityClass) {
        this.userEntityClass = userEntityClass;
    }
}
