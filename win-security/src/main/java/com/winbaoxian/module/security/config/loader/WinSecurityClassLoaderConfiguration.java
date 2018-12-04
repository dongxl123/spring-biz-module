package com.winbaoxian.module.security.config.loader;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.Data;

@Data
public class WinSecurityClassLoaderConfiguration {

    private Class<? extends WinSecurityBaseUserDTO> userDTOClass;
    private Class<? extends WinSecurityBaseUserEntity> userEntityClass;
    private Class<? extends WinSecurityBaseRoleDTO> roleDTOClass;
    private Class<? extends WinSecurityBaseRoleEntity> roleEntityClass;

    public enum ClassNameEnum {

        CLASS_EXTENSION_USER_DTO("userDTOClass"),
        CLASS_EXTENSION_USER_ENTITY("userEntityClass"),
        CLASS_EXTENSION_ROLE_DTO("roleDTOClass"),
        CLASS_EXTENSION_ROLE_ENTITY("roleEntityClass"),
        ;

        private final String value;

        ClassNameEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
