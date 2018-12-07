package com.winbaoxian.module.security.config;

/**
 * @author dongxuanliang252
 */
public enum ControllerEffectiveScope {
    /**
     * @see ControllerEffectiveScopeSelector
     * 优先级 NONE > ALL > OTHER
     * NONE: all controller is ineffective
     * ALL: all controller is effective
     * USER:{@link com.winbaoxian.module.security.controller.WinSecurityUserController} UserController is effective
     * ROLE:{@link com.winbaoxian.module.security.controller.WinSecurityRoleController} RoleController is effective
     * RESOURCE:{@link com.winbaoxian.module.security.controller.WinSecurityResourceController} ResourceController is effective
     * ACCESS:{@link com.winbaoxian.module.security.controller.WinSecurityAccessController} AccessController is effective
     */
    NONE,
    ALL,
    USER,
    ROLE,
    RESOURCE,
    ACCESS,

}