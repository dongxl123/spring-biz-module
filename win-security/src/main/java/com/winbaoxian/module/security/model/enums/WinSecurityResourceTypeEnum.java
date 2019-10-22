package com.winbaoxian.module.security.model.enums;

/**
 * 资源类别, 0:无特别作用，1:菜单，2:其他
 */
public enum WinSecurityResourceTypeEnum {
    DEFAULT(0, "无特别作用"),
    MENU(1, "菜单"),
    OTHER(2, "其他"),
    ;
    private Integer value;
    private String desc;

    WinSecurityResourceTypeEnum(Integer value, String desc) {
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
