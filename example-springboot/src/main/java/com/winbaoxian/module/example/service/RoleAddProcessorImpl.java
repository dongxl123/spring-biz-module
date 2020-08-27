package com.winbaoxian.module.example.service;

import com.winbaoxian.module.security.model.dto.WinSecurityRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IRoleAddProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class RoleAddProcessorImpl implements IRoleAddProcessor {

    @Override
    public void preProcess(WinSecurityRoleDTO params) throws WinSecurityException {
        log.info("add role preProcess");
    }

    @Override
    public void customValidateAfterCommon(WinSecurityRoleDTO params) throws WinSecurityException {
        log.info("add role customValidateAfterCommon");
    }

    @Override
    public void customMappingAfterCommon(WinSecurityRoleDTO params, WinSecurityRoleEntity entity) throws WinSecurityException {
        log.info("add role customMappingAfterCommon");
    }


    @Override
    public void postProcess(WinSecurityRoleDTO result) throws WinSecurityException {
        log.info("add role postProcess");
    }
}
