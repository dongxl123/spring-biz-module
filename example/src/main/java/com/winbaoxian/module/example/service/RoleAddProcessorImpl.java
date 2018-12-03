package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityRoleEntity;
import com.winbaoxian.module.security.service.iface.IRoleAddProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Component
@Slf4j
public class RoleAddProcessorImpl implements IRoleAddProcessor<SecurityRoleDTO, SecurityRoleEntity> {

    @Override
    public void preProcess(SecurityRoleDTO dto) {
        log.info("add role preProcess");
    }

    @Override
    public void preSqlProcess(SecurityRoleEntity entity) {
        log.info("add role preSqlProcess");
    }


    @Override
    public void postProcess(SecurityRoleDTO dto) {
        log.info("add role postProcess");
    }
}
