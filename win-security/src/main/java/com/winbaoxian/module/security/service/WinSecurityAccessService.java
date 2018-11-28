package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityResourceTypeEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private WinSecurityUserService winSecurityUserService;

    public List<WinSecurityResourceDTO> getUserResourceList(Long userId) {
        return winSecurityResourceService.getValidResourceListByUserId(userId);
    }

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
            throw new WinSecurityException("验证失败");
        }
    }

    public WinSecurityBaseUserDTO getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated()) {
            throw new WinSecurityException("用户未认证");
        }
        WinSecurityBaseUserDTO user = (WinSecurityBaseUserDTO) subject.getPrincipal();
        return user;
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

    public List<WinSecurityResourceDTO> getAllValidResourceList() {
        List<WinSecurityResourceDTO> resourceList = winSecurityResourceService.getResourceListByStatus(WinSecurityStatusEnum.ENABLED.getValue());
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
        }
        return resourceList.stream().filter(o -> WinSecurityResourceTypeEnum.BUTTON.getValue().equals(o.getResourceType()) && StringUtils.isNotBlank(o.getValue())).collect(Collectors.toList());
    }

}
