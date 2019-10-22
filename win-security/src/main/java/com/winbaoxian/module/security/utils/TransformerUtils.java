package com.winbaoxian.module.security.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongXL
 * @Create 2018-03-14 20:04
 */
public enum TransformerUtils {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(TransformerUtils.class);

    public <T> List<T> transformMapList2ObjectList(List<Map<String, Object>> mapList, Class<T> clazz) {
        if (CollectionUtils.isEmpty(mapList)) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(transformMap2Object(map, clazz));
        }
        return list;
    }


    public <T> T transformMap2Object(Map<String, Object> map, Class<T> clazz) {
        T o = null;
        try {
            o = clazz.newInstance();
            BeanUtils.populate(o, map);
        } catch (InstantiationException e) {
            log.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException", e);
        }
        return o;
    }
}
