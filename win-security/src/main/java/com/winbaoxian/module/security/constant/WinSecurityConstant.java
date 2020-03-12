package com.winbaoxian.module.security.constant;

/**
 * @author dongxuanliang252
 * @date 2018-11-16 15:15
 */
public interface WinSecurityConstant {

    String[] SECURITY_TABLE_ARRAY = {"USER", "ROLE", "RESOURCE", "USER_ROLE", "ROLE_RESOURCE", "SYS_LOG"};
    String[] RESOURCE_SPECIAL_URL_ARRAY = new String[]{"/**/api/winSecurity/v1/access/get*", "/**/api/winSecurity/v1/user/*", "/**/api/winSecurity/v1/resource/*", "/**/api/winSecurity/v1/role/*"};
    String SORT_COLUMN_SEQ = "seq";
    String SORT_COLUMN_ID = "id";
    String[] ENTITY_PACKAGES = {"com.winbaoxian.module.security.model.entity"};
    String BEAN_NAME_SHIRO_FILTER = "shiroFilter";
    String SHIRO_COOKIE_NAME = "SHIRO_COOKIE";

}
