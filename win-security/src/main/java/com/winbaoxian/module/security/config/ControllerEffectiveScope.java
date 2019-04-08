package com.winbaoxian.module.security.config;

import com.winbaoxian.module.security.config.definition.ControllerEffectiveScopeSelector;

/**
 * @author dongxuanliang252
 */
public enum ControllerEffectiveScope {
    /**
     * @see ControllerEffectiveScopeSelector
     * 优先级 NONE > ALL > OTHER
     * NONE: all strategy is ineffective
     * ALL: all strategy is effective
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
