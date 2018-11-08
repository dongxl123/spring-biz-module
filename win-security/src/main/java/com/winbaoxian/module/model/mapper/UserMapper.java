package com.winbaoxian.module.model.mapper;

import com.winbaoxian.module.model.dto.UserDTO;
import com.winbaoxian.module.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toUserEntity(UserDTO dto);

    UserDTO toUserDTO(UserEntity entity);

    List<UserDTO> toUserDTOList(List<UserEntity> entityList);
}
