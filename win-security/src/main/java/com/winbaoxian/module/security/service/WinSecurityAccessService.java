package com.winbaoxian.module.security.service;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.model.dto.*;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.exceptions.WinSecurityUnAuthException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-11-19 14:04
 */
@Service
@Slf4j
public class WinSecurityAccessService {

    @Resource
    private WinSecurityResourceService winSecurityResourceService;
    @Resource
    private WinSecurityRoleService winSecurityRoleService;
    @Resource
    private WinSecurityUserService winSecurityUserService;

    public void login(String userName) {
        loginInternal(userName, null);
    }

    public void login(Long userId) {
        WinSecurityBaseUserDTO userDTO = winSecurityUserService.getUser(userId);
        if (userDTO == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        loginInternal(userDTO.getUserName(), null);
    }

    public void login(String userName, String password) {
        if (StringUtils.isBlank(userName)) {
            throw new WinSecurityException("账号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new WinSecurityException("密码不能为空");
        }
        String validPassword = StringUtils.upperCase(DigestUtils.md5DigestAsHex(new String(userName + "@winbaoxian.com").getBytes()));
        if (!password.equals(validPassword)) {
            throw new WinSecurityException("密码不正确");
        }
        loginInternal(userName, password);
    }

    private void loginInternal(String userName, String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (ExcessiveAttemptsException e) {
            throw new WinSecurityException("登录失败次数过多");
        } catch (LockedAccountException e) {
            throw new WinSecurityException("帐号已被锁定");
        } catch (DisabledAccountException e) {
            throw new WinSecurityException("帐号已被禁用");
        } catch (ExpiredCredentialsException e) {
            throw new WinSecurityException("帐号已过期");
        } catch (UnknownAccountException e) {
            throw new WinSecurityException("帐号不存在");
        } catch (UnauthorizedException e) {
            throw new WinSecurityException("您没有得到相应的授权");
        } catch (Exception e) {
            log.error("winSecurity loginInternal error", e);
            throw new WinSecurityException("验证失败");
        }
    }

    public boolean logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {
            log.error("logout failed", e);
            return false;
        }
        return true;
    }

    public boolean isAuthenticated() {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isAuthenticated();
    }


    public WinSecurityPrincipal getWinSecurityPrincipal() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated()) {
            log.error("WinSecurityUnAuthException, subject:{}", subject == null ? "null" : "isAuthenticated = false");
            throw new WinSecurityUnAuthException("用户未认证");
        }
        return (WinSecurityPrincipal) subject.getPrincipal();
    }

    public WinSecurityBaseUserDTO getLoginUserInfo() {
        WinSecurityPrincipal principal = getWinSecurityPrincipal();
        return winSecurityUserService.getUser(principal.getId());
    }

    public List<WinSecurityBaseRoleDTO> getLoginUserRoleList() {
        WinSecurityPrincipal principal = getWinSecurityPrincipal();
        return winSecurityRoleService.getValidRoleListByUserId(principal.getId());
    }

    public List<WinSecurityResourceDTO> getLoginUserResourceList() {
        WinSecurityPrincipal principal = getWinSecurityPrincipal();
        return winSecurityResourceService.getValidResourceListByUserId(principal.getId());
    }

    public WinSecurityUserAllInfoDTO getLoginUserAllInfo() {
        WinSecurityPrincipal principal = getWinSecurityPrincipal();
        WinSecurityUserAllInfoDTO allInfoDTO = new WinSecurityUserAllInfoDTO();
        allInfoDTO.setUserInfo(winSecurityUserService.getUser(principal.getId()));
        allInfoDTO.setRoleList(winSecurityRoleService.getValidRoleListByUserId(principal.getId()));
        allInfoDTO.setResourceList(winSecurityResourceService.getValidResourceListByUserId(principal.getId()));
        return allInfoDTO;
    }

}
