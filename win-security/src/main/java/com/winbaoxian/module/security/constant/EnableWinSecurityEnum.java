package com.winbaoxian.module.security.constant;

/**
 * @author dongxuanliang252
 * @date 2018-12-04 18:15
 */
public enum EnableWinSecurityEnum {

    EXTENSION_USER_DTO("extensionUserDTO"),
    EXTENSION_USER_ENTITY("extensionUserEntity"),
    EXTENSION_ROLE_DTO("extensionRoleDTO"),
    EXTENSION_ROLE_ENTITY("extensionRoleEntity"),
    EXTENSION_SERVICE_PROCESSORS("extensionServiceProcessors"),
    ;

    private final String value;

    EnableWinSecurityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
