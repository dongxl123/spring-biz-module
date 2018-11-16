package com.winbaoxian.module.security.model.mapper;

import com.winbaoxian.module.security.model.dto.WinSecurityRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WinSecurityRoleMapper {

    WinSecurityRoleMapper INSTANCE = Mappers.getMapper(WinSecurityRoleMapper.class);

    WinSecurityRoleEntity toRoleEntity(WinSecurityRoleDTO dto);

    WinSecurityRoleDTO toRoleDTO(WinSecurityRoleEntity entity);

    List<WinSecurityRoleDTO> toRoleDTOList(List<WinSecurityRoleEntity> entityList);
}
