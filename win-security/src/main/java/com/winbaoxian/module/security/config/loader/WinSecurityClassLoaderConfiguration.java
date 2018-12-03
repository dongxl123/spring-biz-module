package com.winbaoxian.module.security.config.loader;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
public class WinSecurityClassLoaderConfiguration {

    @Value("${win.security.class.userDTO:WinSecurityBaseUserDTO}")
    private String userDTOClassName;
    @Value("${win.security.class.userEntity:WinSecurityBaseUserEntity}")
    private String userEntityClassName;

    @Value("${win.security.class.roleDTO:WinSecurityBaseRoleDTO}")
    private String roleDTOClassName;
    @Value("${win.security.class.roleEntity:WinSecurityBaseRoleEntity}")
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
            log.error("WinSecurity: win.security.class.userDTO not set, prepare to use default class");
            userDTOClass = WinSecurityBaseUserDTO.class;
        }
        log.info("WinSecurity: UserDTOClass loaded, {}", userDTOClass.getName());

        try {
            userEntityClass = (Class<? extends WinSecurityBaseUserEntity>) ClassUtils.forName(userEntityClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: win.security.class.userEntity not set, prepare to use default class");
            userEntityClass = WinSecurityBaseUserEntity.class;
        }
        log.info("WinSecurity: UserEntityClass loaded, {}", userEntityClass.getName());

        try {
            roleDTOClass = (Class<? extends WinSecurityBaseRoleDTO>) ClassUtils.forName(roleDTOClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: win.security.class.roleDTO not set, prepare to use default class");
            roleDTOClass = WinSecurityBaseRoleDTO.class;
        }
        log.info("WinSecurity: RoleDTOClass loaded, {}", roleDTOClass.getName());

        try {
            roleEntityClass = (Class<? extends WinSecurityBaseRoleEntity>) ClassUtils.forName(roleEntityClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("WinSecurity: win.security.class.roleEntity not set, prepare to use default class");
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
