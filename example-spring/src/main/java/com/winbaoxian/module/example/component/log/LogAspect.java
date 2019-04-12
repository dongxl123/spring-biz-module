package com.winbaoxian.module.example.component.log;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DongXL
 * @Create 2016-12-15 15:02
 */

@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) ||@annotation(org.springframework.web.bind.annotation.PutMapping)||@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object frontIntegrationLog(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        if (logger.isDebugEnabled() && needLog(jp.getSignature().getName())) {
            sb.append("execute method : ").append(jp.getSignature());
            Object[] args = jp.getArgs();
            for (Object arg : args) {
                if (arg instanceof MultipartFile) {
                    sb.append("\n").append("args: ").append(((MultipartFile) arg).getOriginalFilename()).append("\n");
                } else if (!(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)) {
                    sb.append("\n").append("args: ").append(JSON.toJSONString(arg));
                }
            }
        }
        Object ret;
        try {
            if (logger.isDebugEnabled() && needLog(jp.getSignature().getName())) {
                logger.debug("start "+ sb.toString());
            }
            ret = jp.proceed();
            if (logger.isDebugEnabled() && needLog(jp.getSignature().getName())) {
                sb.append("\n").append("return: ").append(JSON.toJSONString(ret));
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled() && needLog(jp.getSignature().getName())) {
                sb.append("\n").append("throw exception: ").append(e.getMessage());
            }
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            if (logger.isDebugEnabled() && needLog(jp.getSignature().getName())) {
                sb.append("\n").append("costTime: [").append(costTime).append("]ms");
                logger.debug("finish "+ sb.toString());
            }
        }
        return ret;
    }

    private boolean needLog(String methodName) {
        if ("chktomcat".equals(methodName)) {
            return false;
        }
        return true;
    }

}
