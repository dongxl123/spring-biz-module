package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserUpdateProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class UserUpdateProcessorImpl implements IUserUpdateProcessor<SecurityUserDTO, SecurityUserEntity> {


    @Override
    public void preProcess(SecurityUserDTO params) throws WinSecurityException {
        log.info("update user preProcess");
    }

    @Override
    public void customValidateAfterCommon(SecurityUserDTO params) throws WinSecurityException {
        log.info("update user customValidateAfterCommon");
    }

    @Override
    public void customMappingAfterCommon(SecurityUserDTO params, SecurityUserEntity entity) throws WinSecurityException {
        log.info("update user customMappingAfterCommon");
    }

    @Override
    public void postProcess(SecurityUserDTO result) throws WinSecurityException {
        log.info("update user postProcess");
        throw new WinSecurityException("transaction test");
    }
}
