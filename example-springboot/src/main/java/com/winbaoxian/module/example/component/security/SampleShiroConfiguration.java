package com.winbaoxian.module.example.component.security;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongxuanliang252
 * @date 2019-04-22 11:47
 */
@Configuration
public class SampleShiroConfiguration {

    @Bean
    public Cookie cookie() {
        Cookie cookie = new SimpleCookie("SHIRO_COOKIE");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 30);
        return cookie;
    }
}
