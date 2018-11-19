package com.winbaoxian.module.security.model.mapper;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class WinSecurityRoleMapper<D extends WinSecurityBaseRoleDTO, E extends WinSecurityBaseRoleEntity> {

    public static final WinSecurityRoleMapper INSTANCE = new WinSecurityRoleMapper();

    public E toRoleEntity(D dto, Class<E> entityClass) {
        return JSON.parseObject(JSON.toJSONString(dto), entityClass);
    }

    public D toRoleDTO(E entity, Class<D> dtoClass) {
        return JSON.parseObject(JSON.toJSONString(entity), dtoClass);
    }

    public List<D> toRoleDTOList(List<E> entityList, Class<D> dtoClass) {
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            dtoList.add(toRoleDTO(e, dtoClass));
        }
        return dtoList;
    }
}
