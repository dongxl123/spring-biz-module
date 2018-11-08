package com.winbaoxian.module.model.mapper;

import com.winbaoxian.module.model.dto.RoleDTO;
import com.winbaoxian.module.model.entity.RoleEntity;
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
