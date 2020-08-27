package com.winbaoxian.module.security.model.mapper;

import com.winbaoxian.module.security.model.dto.WinSecurityUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")

public interface WinSecurityUserMapper {

    WinSecurityUserMapper INSTANCE = Mappers.getMapper(WinSecurityUserMapper.class);

    WinSecurityUserEntity toUserEntity(WinSecurityUserDTO dto);

    WinSecurityUserDTO toUserDTO(WinSecurityUserEntity entit);

    List<WinSecurityUserDTO> toUserDTOList(List<WinSecurityUserEntity> entityList);
}
