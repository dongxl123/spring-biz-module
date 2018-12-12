package com.winbaoxian.module.security.constant;

/**
 * @author dongxuanliang252
 * @date 2018-11-16 15:15
 */
public class WinSecurityConstant {

    public static final String[] SECURITY_TABLE_ARRAY = {"USER", "ROLE", "RESOURCE", "USER_ROLE", "ROLE_RESOURCE","SYS_LOG"};
    public static final String[] RESOURCE_SPECIAL_URL_ARRAY = new String[]{"/api/winSecurity/v1/user/*", "/api/winSecurity/v1/resource/*", "/api/winSecurity/v1/role/*"};
    public static final String SORT_COLUMN_SEQ = "seq";
    public static final String[] ENTITY_PACKAGES = {"com.winbaoxian.module.security.model.entity"};


}
