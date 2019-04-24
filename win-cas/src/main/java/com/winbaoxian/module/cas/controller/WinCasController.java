package com.winbaoxian.module.cas.controller;

import com.winbaoxian.module.cas.config.WinCasClientConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dongxuanliang252
 * @date 2019-04-11 9:45
 */
@Controller
@RequestMapping("/api/winCas")
@Slf4j
public class WinCasController {

    @Autowired
    private WinCasClientConfigurationProperties configProps;

    @RequestMapping("auth")
    public String auth(HttpServletRequest request, String callback) {
        String uid = request.getRemoteUser();
        log.info("cas user {} authenticated", uid);
        return "redirect:" + callback;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        StringBuilder sb = new StringBuilder("redirect:").append(configProps.getServerLogoutUrl()).append("?service=").append(configProps.getLogoutRedirectUrl());
        return sb.toString();
    }

}
