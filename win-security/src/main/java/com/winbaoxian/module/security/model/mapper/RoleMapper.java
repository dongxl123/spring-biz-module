package com.winbaoxian.module.security.model.mapper;

import com.winbaoxian.module.security.model.dto.RoleDTO;
import com.winbaoxian.module.security.model.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleEntity toRoleEntity(RoleDTO dto);

    RoleDTO toRoleDTO(RoleEntity entity);

    List<RoleDTO> toRoleDTOList(List<RoleEntity> entityList);
}
