package com.winbaoxian.module.model.mapper;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.config.UserConfiguration;
import com.winbaoxian.module.model.dto.BaseUserDTO;
import com.winbaoxian.module.model.entity.BaseUserEntity;
import org.aspectj.weaver.ClassAnnotationValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;
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
