package com.winbaoxian.module.example.service;

import com.winbaoxian.module.security.model.dto.WinSecurityUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserAddProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class UserAddProcessorImpl implements IUserAddProcessor {


    @Override
    public void preProcess(WinSecurityUserDTO params) throws WinSecurityException {
        log.info("add user preProcess");
    }

    @Override
    public void customValidateAfterCommon(WinSecurityUserDTO params) throws WinSecurityException {
        log.info("add user customValidateAfterCommon");
    }

    @Override
    public void customMappingAfterCommon(WinSecurityUserDTO params, WinSecurityUserEntity entity) throws WinSecurityException {
        log.info("add user customMappingAfterCommon");
    }

    @Override
    public void postProcess(WinSecurityUserDTO result) throws WinSecurityException {
        log.info("add user postProcess");
    }
}
