package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityUserDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityUserEntity;
import com.winbaoxian.module.security.service.iface.IUserAddProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
//@Component
@Slf4j
public class UserAddProcessorImpl implements IUserAddProcessor<SecurityUserDTO, SecurityUserEntity> {


    @Override
    public void preProcess(SecurityUserDTO dto) {
        log.info("add user preProcess");
    }

    @Override
    public void preSqlProcess(SecurityUserEntity entity) {
        log.info("add user preSqlProcess");
    }

    @Override
    public void postProcess(SecurityUserDTO dto) {
        log.info("add user postProcess");
    }
}
