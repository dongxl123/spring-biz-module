package com.winbaoxian.module.model.mapper;


import com.winbaoxian.module.model.dto.ResourceDTO;
import com.winbaoxian.module.model.entity.ResourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    ResourceEntity toResourceEntity(ResourceDTO dto);

    ResourceDTO toResourceDTO(ResourceEntity entity);

    List<ResourceDTO> toResourceDTOList(List<ResourceEntity> entityList);
}
