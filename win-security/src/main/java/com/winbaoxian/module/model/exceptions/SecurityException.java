package com.winbaoxian.module.model.exceptions;


import com.winbaoxian.module.model.enums.SecurityErrorEnum;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:00
 */
public class SecurityException extends RuntimeException {

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(SecurityErrorEnum securityError) {
        super(securityError.getMessage());
    }


}
