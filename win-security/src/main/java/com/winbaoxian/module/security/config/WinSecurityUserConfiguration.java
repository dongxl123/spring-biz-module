package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
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

    @Value("${security.class.roleDTO:WinSecurityBaseRoleDTO}")
    private String roleDTOClassName;
    @Value("${security.class.roleEntity:WinSecurityBaseRoleEntity}")
    private String roleEntityClassName;

    private Class<? extends WinSecurityBaseUserDTO> userDTOClass;
    private Class<? extends WinSecurityBaseUserEntity> userEntityClass;
    private Class<? extends WinSecurityBaseRoleDTO> roleDTOClass;
    private Class<? extends WinSecurityBaseRoleEntity> roleEntityClass;

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

        try {
            roleDTOClass = (Class<? extends WinSecurityBaseRoleDTO>) ClassUtils.forName(roleDTOClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.roleDTO not set, prepare to use default class");
            roleDTOClass = WinSecurityBaseRoleDTO.class;
        }
        log.info("WinSecurity: RoleDTOClass loaded, {}", roleDTOClass.getName());

        try {
            roleEntityClass = (Class<? extends WinSecurityBaseRoleEntity>) ClassUtils.forName(roleEntityClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: security.class.roleEntity not set, prepare to use default class");
            roleEntityClass = WinSecurityBaseRoleEntity.class;
        }
        log.info("WinSecurity: RoleEntityClass loaded, {}", roleEntityClass.getName());
    }

    public Class<? extends WinSecurityBaseUserDTO> getUserDTOClass() {
        return userDTOClass;
    }

    public Class<? extends WinSecurityBaseUserEntity> getUserEntityClass() {
        return userEntityClass;
    }

    public Class<? extends WinSecurityBaseRoleDTO> getRoleDTOClass() {
        return roleDTOClass;
    }

    public Class<? extends WinSecurityBaseRoleEntity> getRoleEntityClass() {
        return roleEntityClass;
    }

}
