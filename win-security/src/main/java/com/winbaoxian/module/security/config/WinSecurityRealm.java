package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.repository.WinSecurityUserRepository;
import com.winbaoxian.module.security.service.WinSecurityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author dongxuanliang252
 * @date 2018-11-23 20:39
 */
@Component
public class WinSecurityRealm extends AuthorizingRealm {

    @Resource
    private WinSecurityUserRepository winSecurityUserRepository;

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return new AllowAllCredentialsMatcher();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        WinSecurityBaseUserEntity user = winSecurityUserRepository.findOneByUserNameAndDeletedFalse(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + token.getUsername() + "]");
        }
        if (WinSecurityStatusEnum.DISABLED.getValue().equals(user.getStatus())) {
            throw new DisabledAccountException("account is disables for user [" + token.getUsername() + "]");
        }
        return new SimpleAuthenticationInfo(user.getUserName(), null, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

}
