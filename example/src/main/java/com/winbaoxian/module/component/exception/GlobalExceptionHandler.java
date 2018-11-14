package com.winbaoxian.module.component.exception;

import com.winbaoxian.module.model.exceptions.BusinessException;
import com.winbaoxian.module.model.exceptions.WinSecurityException;
import com.winbaoxian.standard.model.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理类
 *
 * @Author DongXL
 * @Create 2016-12-07 17:54
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ResponseStatus
    @ExceptionHandler(WinSecurityException.class)
    @ResponseBody
    public Object handleWinSecurityExp(WinSecurityException e) {
        logger.error("winSecurity exception handler  " + e.getMessage());
        return JsonResult.createErrorResult(e.getMessage());
    }

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleCommonExp(Exception e) {
        logger.error("common exception handler  " + e.getMessage(), e);
        return JsonResult.createErrorResult("服务器内部问题");
    }


    @ResponseStatus
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Object handleBusinessExp(BusinessException e) {
        logger.error("business exception handler  " + e.getMessage());
        return JsonResult.createErrorResult(e.getMessage());
    }


}
