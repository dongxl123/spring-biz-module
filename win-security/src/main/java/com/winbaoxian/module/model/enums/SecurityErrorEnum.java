package com.winbaoxian.module.model.enums;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:01
 */
public enum SecurityErrorEnum {

    COMMON_DATA_EXISTS("数据已存在"),
    COMMON_DATA_NOT_EXISTS("数据不存在"),
    ;

    private String message;

    SecurityErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
