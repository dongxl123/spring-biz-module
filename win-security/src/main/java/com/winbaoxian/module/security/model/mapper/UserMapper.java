package com.winbaoxian.module.security.model.mapper;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.model.dto.BaseUserDTO;
import com.winbaoxian.module.security.model.entity.BaseUserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMapper<D extends BaseUserDTO, E extends BaseUserEntity> {

    public static final UserMapper INSTANCE = new UserMapper();

    public E toUserEntity(D dto, Class<E> entityClass) {
        return JSON.parseObject(JSON.toJSONString(dto), entityClass);
    }

    public D toUserDTO(E entity, Class<D> dtoClass) {
        return JSON.parseObject(JSON.toJSONString(entity), dtoClass);
    }

    public List<D> toUserDTOList(List<E> entityList, Class<D> dtoClass) {
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            dtoList.add(toUserDTO(e, dtoClass));
        }
        return dtoList;
    }
}
