package com.winbaoxian.module.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dongxuanliang252
 * @date 2019-04-04 11:39
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public Object test(HttpServletRequest request){
        return request.getRemoteUser();
    }
}
