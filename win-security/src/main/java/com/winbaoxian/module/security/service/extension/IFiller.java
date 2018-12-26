package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.exceptions.WinSecurityException;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-12-26 17:15
 */
public interface IFiller<D> {

    void fillData(D dto) throws WinSecurityException;

    void fillData(List<D> dtoList) throws WinSecurityException;

}
