package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
public abstract class IUserPageProcessor<D extends WinSecurityBaseUserDTO, E extends WinSecurityBaseUserEntity> implements IUserProcessor<D, E> {

    @Override
    public void customMappingAfterCommon(D params, E entity) throws WinSecurityException {
    }

    @Override
    public void postProcess(D result) throws WinSecurityException {
    }

}
