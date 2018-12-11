package com.winbaoxian.module.security.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-11 17:20
 */
public enum MapFastUtils {
    INSTANCE;
    public Map valueEmptyToNull(Map map) {
        for (Object k : map.keySet()) {
            Object v = map.get(k);
            if (v == null) {
                continue;
            } else if (v instanceof String && StringUtils.isBlank((CharSequence) v)) {
                map.replace(k, null);
                continue;
            } else if (v instanceof Collection && CollectionUtils.isEmpty((Collection) v)) {
                map.replace(k, null);
                continue;
            }
        }
        return map;
    }
}
