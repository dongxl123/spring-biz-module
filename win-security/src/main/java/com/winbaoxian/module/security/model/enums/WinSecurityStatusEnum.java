package com.winbaoxian.module.security.model.enums;

public enum WinSecurityStatusEnum {

    ENABLED(1, "有效"),
    DISABLED(0, "失效"),
    ;

    private Integer value;
    private String desc;

    WinSecurityStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
