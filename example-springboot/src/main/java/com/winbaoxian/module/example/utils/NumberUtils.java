package com.winbaoxian.module.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public enum NumberUtils {

    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Double doubleNullToZero(Double value) {
        if (value == null) {
            return 0d;
        }
        return value;
    }

    public Integer parse2Int(Object o) {
        try {
            return new BigDecimal(o.toString()).intValue();
        } catch (Exception e) {
            logger.error("NumberUtils.parse2Int error, {}", o, e);
        }
        return null;
    }

    public Long parse2Long(Object o) {
        try {
            return new BigDecimal(o.toString()).longValue();
        } catch (Exception e) {
            logger.error("NumberUtils.parse2Long error, {}", o, e);
        }
        return null;
    }

    public Double parse2Double(Object o) {
        try {
            return new BigDecimal(o.toString()).doubleValue();
        } catch (Exception e) {
            logger.error("NumberUtils.parse2Int error, {}", o, e);
        }
        return null;
    }

}
