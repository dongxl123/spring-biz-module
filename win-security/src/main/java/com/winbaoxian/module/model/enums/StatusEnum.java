package com.winbaoxian.module.model.enums;

public enum StatusEnum {

    DISABLED(0, "失效"),
    ENABLED(1, "有效"),
    ;

    private Integer value;
    private String desc;

    StatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
