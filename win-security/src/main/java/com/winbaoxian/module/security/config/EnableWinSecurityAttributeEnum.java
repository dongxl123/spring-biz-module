package com.winbaoxian.module.security.config;

/**
 * @author dongxuanliang252
 * @date 2018-12-04 18:15
 */
public enum EnableWinSecurityAttributeEnum {

    ENTITY_MANAGER_FACTORY_REF("entityManagerFactoryRef"),
    TRANSACTION_MANAGER_REF("transactionManagerRef"),
    ENTITY_SCAN_PACKAGES("entityScanPackages"),
    CONTROLLER_SCOPES("controllerScopes"),
    EXTENSION_USER_DTO("extensionUserDTO"),
    EXTENSION_USER_ENTITY("extensionUserEntity"),
    EXTENSION_ROLE_DTO("extensionRoleDTO"),
    EXTENSION_ROLE_ENTITY("extensionRoleEntity"),
    EXTENSION_SERVICE_PROCESSORS("extensionServiceProcessors"),
    ;

    private final String value;

    EnableWinSecurityAttributeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
