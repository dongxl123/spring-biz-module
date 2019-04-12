package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserPageProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class UserPageProcessorImpl extends IUserPageProcessor<SecurityUserDTO, SecurityUserEntity> {


    @Override
    public void preProcess(SecurityUserDTO params) throws WinSecurityException {
        log.info("UserPageProcessorImpl preProcess");
    }

    @Override
    public void customValidateAfterCommon(SecurityUserDTO params) throws WinSecurityException {
        log.info("UserPageProcessorImpl customValidateAfterCommon");
    }
}
