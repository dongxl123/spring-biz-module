package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.security.annotation.ServiceExtension;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserAddProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@ServiceExtension
@Slf4j
public class UserAddProcessorImpl implements IUserAddProcessor<SecurityUserDTO, SecurityUserEntity> {


    @Override
    public void preProcess(SecurityUserDTO dto) throws WinSecurityException {
        log.info("add user preProcess");
    }

    @Override
    public void customValidateAfterCommon(SecurityUserDTO dto) throws WinSecurityException {
        log.info("add user customValidateAfterCommon");
    }

    @Override
    public void customMappingAfterCommon(SecurityUserDTO dto, SecurityUserEntity entity) throws WinSecurityException {
        log.info("add user customMappingAfterCommon");
    }

    @Override
    public void postProcess(SecurityUserDTO dto) throws WinSecurityException {
        log.info("add user postProcess");
    }
}
