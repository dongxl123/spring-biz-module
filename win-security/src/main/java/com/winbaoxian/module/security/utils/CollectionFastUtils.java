package com.winbaoxian.module.security.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author dongxuanliang252
 * @date 2018-12-12 15:09
 */
public enum CollectionFastUtils {
    INSTANCE;

    public boolean isDistinctEqualCollection(Collection a, Collection b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null || b == null) {
            return false;
        }
        return CollectionUtils.isEqualCollection(new HashSet<>(a), new HashSet<>(b));
    }

}
