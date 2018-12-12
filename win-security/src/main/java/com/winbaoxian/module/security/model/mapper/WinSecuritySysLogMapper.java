package com.winbaoxian.module.security.model.mapper;

import com.winbaoxian.module.security.model.dto.WinSecuritySysLogDTO;
import com.winbaoxian.module.security.model.entity.WinSecuritySysLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author dongxuanliang252
 * @date 2018-12-12 11:39
 */
@Mapper
public interface WinSecuritySysLogMapper {

    WinSecuritySysLogMapper INSTANCE = Mappers.getMapper(WinSecuritySysLogMapper.class);

    WinSecuritySysLogEntity toSysLogEntity(WinSecuritySysLogDTO dto);

    WinSecuritySysLogDTO toSysLogDTO(WinSecuritySysLogEntity entity);
}
