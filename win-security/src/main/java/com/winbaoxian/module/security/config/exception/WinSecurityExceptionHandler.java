package com.winbaoxian.module.security.config.exception;

import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.enums.JsonResultCodeEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.exceptions.WinSecurityUnAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局存在唯一个，当项目中已经定义了，改类无作用
 */
@RestControllerAdvice
public class WinSecurityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WinSecurityExceptionHandler.class);

    @ResponseStatus
    @ExceptionHandler(WinSecurityException.class)
    public Object handleWinSecurityExp(WinSecurityException e) {
        logger.error("winSecurity exception handler  " + e.getMessage());
        return JsonResult.createErrorResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(WinSecurityUnAuthException.class)
    public Object handleWinSecurityExp(WinSecurityUnAuthException e) {
        logger.error("winSecurity unAuthException handler  " + e.getMessage());
        return JsonResult.createNewInstance(JsonResultCodeEnum.UNAUTHORIZED, e.getMessage(), null);
    }

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public Object handleCommonExp(Exception e) {
        logger.error("common exception handler  " + e.getMessage(), e);
        return JsonResult.createErrorResult("服务器内部问题");
    }

}