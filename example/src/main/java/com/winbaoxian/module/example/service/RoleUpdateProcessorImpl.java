package com.winbaoxian.module.example.service;

import com.winbaoxian.module.example.model.dto.SecurityRoleDTO;
import com.winbaoxian.module.example.model.entity.citymanager.SecurityRoleEntity;
import com.winbaoxian.module.security.service.extension.IRoleUpdateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:50
 */
@Component
@Slf4j
public class RoleUpdateProcessorImpl implements IRoleUpdateProcessor<SecurityRoleDTO, SecurityRoleEntity> {

    @Override
    public void preProcess(SecurityRoleDTO dto) {
        log.info("update role preProcess");
    }

    @Override
    public void preSqlProcess(SecurityRoleEntity entity) {
        log.info("update role preSqlProcess");
    }


    @Override
    public void postProcess(SecurityRoleDTO dto) {
        log.info("update role postProcess");
    }
}
