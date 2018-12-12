package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.dto.WinSecuritySysLogDTO;
import com.winbaoxian.module.security.model.entity.WinSecuritySysLogEntity;
import com.winbaoxian.module.security.model.mapper.WinSecuritySysLogMapper;
import com.winbaoxian.module.security.repository.WinSecuritySysLogRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dongxuanliang252
 * @date 2018-12-12 11:41
 */
@Service
public class WinSecuritySysLogService {

    @Resource
    private WinSecuritySysLogRepository winSecuritySysLogRepository;

    public WinSecuritySysLogDTO addSysLog(WinSecuritySysLogDTO dto) {
        WinSecuritySysLogEntity entity = WinSecuritySysLogMapper.INSTANCE.toSysLogEntity(dto);
        WinSecuritySysLogEntity retEntity = winSecuritySysLogRepository.save(entity);
        return WinSecuritySysLogMapper.INSTANCE.toSysLogDTO(retEntity);
    }

}
