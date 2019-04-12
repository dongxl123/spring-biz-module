package com.winbaoxian.module.example.model.exceptions;


import com.winbaoxian.module.example.model.enums.BusinessErrorEnum;

/**
 * @Author DongXL
 * @Create 2018-03-26 15:00
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessErrorEnum businessError) {
        super(businessError.getMessage());
    }


}
