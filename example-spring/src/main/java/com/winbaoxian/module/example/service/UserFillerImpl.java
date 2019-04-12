package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.extension.IUserFiller;

import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-12-26 17:30
 */
public class UserFillerImpl implements IUserFiller<SecurityUserDTO> {

    @Override
    public void fillData(SecurityUserDTO dto) throws WinSecurityException {
        dto.setFillEdu("test");
    }

    @Override
    public void fillData(List<SecurityUserDTO> dtoList) throws WinSecurityException {
        for (SecurityUserDTO dto : dtoList) {
            dto.setFillEdu("testList");
        }
    }
}
