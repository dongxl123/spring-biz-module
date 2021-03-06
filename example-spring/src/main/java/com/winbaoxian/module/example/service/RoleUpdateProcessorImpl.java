package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityRoleEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IRoleUpdateProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class RoleUpdateProcessorImpl implements IRoleUpdateProcessor<SecurityRoleDTO, SecurityRoleEntity> {

    @Override
    public void preProcess(SecurityRoleDTO params) throws WinSecurityException {
        log.info("update role preProcess");
    }

    @Override
    public void customValidateAfterCommon(SecurityRoleDTO params) throws WinSecurityException {
        log.info("update role customValidateAfterCommon");
    }

    @Override
    public void customMappingAfterCommon(SecurityRoleDTO params, SecurityRoleEntity entity) throws WinSecurityException {
        log.info("update role customMappingAfterCommon");
    }


    @Override
    public void postProcess(SecurityRoleDTO result) throws WinSecurityException {
        log.info("update role postProcess");
    }
}
