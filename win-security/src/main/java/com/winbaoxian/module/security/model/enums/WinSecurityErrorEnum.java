package com.winbaoxian.module.security.model.enums;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:01
 */
public enum WinSecurityErrorEnum {

    COMMON_PARAM_NOT_EXISTS("缺少参数"),
    COMMON_RESOURCE_NOT_EXISTS("资源不存在"),
    COMMON_USER_EXISTS("用户已存在"),
    COMMON_USER_NOT_EXISTS("用户不存在"),
    COMMON_ROLE_NOT_EXISTS("角色不存在"),
    ;

    private String message;

    WinSecurityErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}