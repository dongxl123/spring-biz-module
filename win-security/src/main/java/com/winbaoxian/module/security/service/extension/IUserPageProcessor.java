package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.dto.WinSecurityUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
public abstract class IUserPageProcessor implements IUserProcessor {

    @Override
    public void customMappingAfterCommon(WinSecurityUserDTO params, WinSecurityUserEntity entity) throws WinSecurityException {
    }

    @Override
    public void postProcess(WinSecurityUserDTO result) throws WinSecurityException {
    }

}
