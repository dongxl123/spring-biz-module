package com.winbaoxian.module.security.model.enums;

/**
 * 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
 */
public enum ResourceTypeEnum {
    DEFAULT(0, "无特别作用"),
    MENU(1, "菜单"),
    SUB_PAGE(2, "子页面"),
    BUTTON(3, "按钮"),
    VARIABLE(4, "页面自定义变量"),
    ;
    private Integer value;
    private String desc;

    ResourceTypeEnum(Integer value, String desc) {
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
