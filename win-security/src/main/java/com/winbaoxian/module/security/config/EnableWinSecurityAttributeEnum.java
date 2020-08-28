package com.winbaoxian.module.security.config;

/**
 * @author dongxuanliang252
 * @date 2018-12-04 18:15
 */
public enum EnableWinSecurityAttributeEnum {

    ENTITY_MANAGER_FACTORY_REF("entityManagerFactoryRef"),
    TRANSACTION_MANAGER_REF("transactionManagerRef"),
    CONTROLLER_SCOPES("controllerScopes"),
    EXTENSION_SERVICE_PROCESSORS("extensionServiceProcessors"),
    SYS_LOG("sysLog"),
    APP_CODE("appCode"),
    ;

    private final String value;

    EnableWinSecurityAttributeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
