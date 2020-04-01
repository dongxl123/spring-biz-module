package com.winbaoxian.module.security.config.shiro;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.dto.CasWinSecurityPrincipal;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import com.winbaoxian.module.security.service.WinSecurityRoleService;
import com.winbaoxian.module.security.service.WinSecurityUserService;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.profile.CommonProfile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dongxuanliang252
 * @date 2018-11-23 20:39
 */
public class Pac4jWinSecurityRealm extends Pac4jRealm {

    private WinSecurityUserService winSecurityUserService;
    private WinSecurityRoleService winSecurityRoleService;
    private WinSecurityResourceService winSecurityResourceService;


    public Pac4jWinSecurityRealm(WinSecurityUserService winSecurityUserService, WinSecurityRoleService winSecurityRoleService, WinSecurityResourceService winSecurityResourceService) {
        this.winSecurityUserService = winSecurityUserService;
        this.winSecurityRoleService = winSecurityRoleService;
        this.winSecurityResourceService = winSecurityResourceService;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        Pac4jToken token = (Pac4jToken) authcToken;
        CommonProfile commonProfile =token.getProfiles().get(0);
        WinSecurityBaseUserDTO user = winSecurityUserService.getUserByUserName(commonProfile.getId());
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + commonProfile.getId() + "]");
        }
        if (WinSecurityStatusEnum.DISABLED.getValue().equals(user.getStatus())) {
            throw new DisabledAccountException("account is disables for user [" + commonProfile.getId() + "]");
        }

        final CasWinSecurityPrincipal principal = new CasWinSecurityPrincipal(token.getProfiles(), getPrincipalNameAttribute(),user.getId(),user.getUserName());
        final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
        return new SimpleAuthenticationInfo(principalCollection, token.getProfiles().hashCode());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null || principals.isEmpty()) {
            throw new AuthorizationException("principals is null.");
        }
        final CasWinSecurityPrincipal principal = principals.oneByType(CasWinSecurityPrincipal.class);
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
