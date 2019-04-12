package com.winbaoxian.module.example.model.enums;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:01
 */
public enum BusinessErrorEnum {

    COMMON_DATA_EXISTS("数据已存在"),
    COMMON_MISSING_PARAM("缺少参数%s"),
    COMMON_DATA_NOT_FOUND("找不到数据"),
    PRODUCT_CONFIG_PRODUCT_CODE_EXISTS("该产品配置已存在"),
    COMMON_USER_NOT_FOUND("找不到用户"),
    COMMON_BROKER_NOT_FOUND("该用户不是签约经纪人"),
    ;

    private String message;

    BusinessErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
