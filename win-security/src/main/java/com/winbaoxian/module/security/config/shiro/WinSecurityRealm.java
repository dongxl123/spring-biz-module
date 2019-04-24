package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityPrincipal;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.service.WinSecurityRoleService;
import com.winbaoxian.module.security.service.WinSecurityUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dongxuanliang252
 * @date 2018-11-23 20:39
 */
public class WinSecurityRealm extends AuthorizingRealm {

    private WinSecurityUserService winSecurityUserService;
    private WinSecurityRoleService winSecurityRoleService;
    private WinSecurityResourceService winSecurityResourceService;

    public WinSecurityRealm() {
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    public WinSecurityRealm(WinSecurityUserService winSecurityUserService, WinSecurityRoleService winSecurityRoleService, WinSecurityResourceService winSecurityResourceService) {
        this();
        this.winSecurityUserService = winSecurityUserService;
        this.winSecurityRoleService = winSecurityRoleService;
        this.winSecurityResourceService = winSecurityResourceService;
    }


    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return new AllowAllCredentialsMatcher();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        WinSecurityBaseUserDTO user = winSecurityUserService.getUserByUserName(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + token.getUsername() + "]");
        }
        if (WinSecurityStatusEnum.DISABLED.getValue().equals(user.getStatus())) {
            throw new DisabledAccountException("account is disables for user [" + token.getUsername() + "]");
        }
        WinSecurityPrincipal principal = new WinSecurityPrincipal();
        principal.setId(user.getId());
        principal.setUserName(user.getUserName());
        return new SimpleAuthenticationInfo(principal, null, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null || principals.isEmpty()) {
            throw new AuthorizationException("principals is null.");
        }
        WinSecurityPrincipal principal = (WinSecurityPrincipal) getAvailablePrincipal(principals);
        Set<String> roleNames = null;
        Set<String> permissions = null;
        List<WinSecurityBaseRoleDTO> roleDTOList = winSecurityRoleService.getValidRoleListByUserId(principal.getId());
        if (CollectionUtils.isNotEmpty(roleDTOList)) {
            roleNames = roleDTOList.stream().map(o -> o.getName()).collect(Collectors.toSet());
        }
        List<WinSecurityResourceDTO> resourceDTOList = winSecurityResourceService.getValidResourceListByUserId(principal.getId());
        if (CollectionUtils.isNotEmpty(resourceDTOList)) {
            permissions = resourceDTOList.stream().filter(o -> StringUtils.isNotBlank(o.getAjaxUrls())).map(o -> String.valueOf(o.getId())).collect(Collectors.toSet());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

}
