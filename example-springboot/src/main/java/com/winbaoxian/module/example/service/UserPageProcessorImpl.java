package com.winbaoxian.module.example.service;

import com.winbaoxian.module.security.model.dto.WinSecurityUserDTO;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserPageProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Slf4j
public class UserPageProcessorImpl extends IUserPageProcessor {


    @Override
    public void preProcess(WinSecurityUserDTO params) throws WinSecurityException {
        log.info("UserPageProcessorImpl preProcess");
    }

    @Override
    public void customValidateAfterCommon(WinSecurityUserDTO params) throws WinSecurityException {
        log.info("UserPageProcessorImpl customValidateAfterCommon");
    }
}
