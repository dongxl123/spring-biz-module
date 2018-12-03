package com.winbaoxian.module.security.model.mapper;


import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WinSecurityResourceMapper {

    WinSecurityResourceMapper INSTANCE = Mappers.getMapper(WinSecurityResourceMapper.class);

    WinSecurityResourceEntity toResourceEntity(WinSecurityResourceDTO dto);

    WinSecurityResourceDTO toResourceDTO(WinSecurityResourceEntity entity);

    List<WinSecurityResourceDTO> toResourceDTOList(List<WinSecurityResourceEntity> entityList);
}
