package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityResourceTypeEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.service.WinSecurityRoleService;
import com.winbaoxian.module.security.service.WinSecurityUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dongxuanliang252
 * @date 2018-11-23 20:39
 */
@Component
public class WinSecurityRealm extends AuthorizingRealm {

    @Resource
    private WinSecurityUserService winSecurityUserService;
    @Resource
    private WinSecurityRoleService winSecurityRoleService;
    @Resource
    private WinSecurityResourceService winSecurityResourceService;

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
        return new SimpleAuthenticationInfo(user, null, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        WinSecurityBaseUserDTO userDTO = (WinSecurityBaseUserDTO) getAvailablePrincipal(principals);
        Set<String> roleNames = null;
        Set<String> permissions = null;
        List<WinSecurityBaseRoleDTO> roleDTOList = winSecurityRoleService.getRoleListByUserId(userDTO.getId());
        if (!CollectionUtils.isEmpty(roleDTOList)) {
            roleNames = roleDTOList.stream().map(o -> o.getName()).collect(Collectors.toSet());
        }
        List<WinSecurityResourceDTO> resourceDTOList = winSecurityResourceService.getValidResourceListByUserId(userDTO.getId());
        if (!CollectionUtils.isEmpty(resourceDTOList)) {
            permissions = resourceDTOList.stream().filter(o -> WinSecurityResourceTypeEnum.BUTTON.getValue().equals(o.getResourceType()) && StringUtils.isNotBlank(o.getValue())).map(o -> o.getValue()).collect(Collectors.toSet());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

}
