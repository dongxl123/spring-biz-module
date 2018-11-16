package com.winbaoxian.module.security.model.exceptions;


import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:00
 */
public class WinSecurityException extends RuntimeException {

    public WinSecurityException(String message) {
        super(message);
    }

    public WinSecurityException(WinSecurityErrorEnum securityError) {
        super(securityError.getMessage());
    }


}
