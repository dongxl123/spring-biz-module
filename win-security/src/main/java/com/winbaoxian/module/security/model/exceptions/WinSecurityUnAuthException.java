package com.winbaoxian.module.security.model.exceptions;


import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:00
 */
public class WinSecurityUnAuthException extends RuntimeException {

    public WinSecurityUnAuthException(String message) {
        super(message);
    }

    public WinSecurityUnAuthException(WinSecurityErrorEnum securityError) {
        super(securityError.getMessage());
    }


}
