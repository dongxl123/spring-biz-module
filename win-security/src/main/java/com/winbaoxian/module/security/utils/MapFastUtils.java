package com.winbaoxian.module.security.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-11 17:20
 */
public enum MapFastUtils {

    INSTANCE;

    public static final String NULL_VALUE = "null";

    public Map valueEmptyToNull(Map map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        Object[] keys = map.keySet().toArray();
        for (Object k : keys) {
            Object v = map.get(k);
            if (v == null) {
                map.remove(k);
            } else if (v instanceof CharSequence && (StringUtils.isBlank((CharSequence) v) || StringUtils.equalsIgnoreCase(NULL_VALUE, (CharSequence) v))) {
                map.remove(k);
            } else if (v instanceof Collection && CollectionUtils.isEmpty((Collection) v)) {
                map.remove(k);
            }
        }
        return map;
    }
}
