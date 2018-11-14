package com.winbaoxian.module.model.exceptions;


import com.winbaoxian.module.model.enums.WinSecurityErrorEnum;

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
