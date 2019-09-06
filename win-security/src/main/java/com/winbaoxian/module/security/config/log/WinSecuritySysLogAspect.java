package com.winbaoxian.module.security.config.log;


import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.dto.WinSecuritySysLogDTO;
import com.winbaoxian.module.security.service.WinSecurityAccessService;
import com.winbaoxian.module.security.service.WinSecuritySysLogService;
import com.winbaoxian.module.security.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description：AOP 日志
 * @author：dongxuanliang252
 * @date：2018年12月12日11:38:09
 */
@Aspect
public class WinSecuritySysLogAspect {

    private static final Logger log = LoggerFactory.getLogger(WinSecuritySysLogAspect.class);

    @Resource
    private WinSecuritySysLogService winSecuritySysLogService;
    @Resource
    private WinSecurityAccessService winSecurityAccessService;

    @Around("(within(com.winbaoxian.module.security.controller..*) && @annotation(org.springframework.web.bind.annotation.PostMapping))||@annotation(com.winbaoxian.module.security.annotation.EnableSysLog)")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //SYSLOG前置处理，防止业务更新对象，导致数据不准
        StringBuffer sb = new StringBuffer();
        try {
            Object[] args = point.getArgs();
            if (ArrayUtils.isNotEmpty(args)) {
                for (Object arg : args) {
                    if (arg instanceof MultipartFile) {
                        sb.append("[").append(ClassUtils.getShortCanonicalName(arg.getClass())).append("]: ").append(((MultipartFile) arg).getOriginalFilename()).append(";");
                    } else if (!(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)) {
                        String value = arg instanceof CharSequence ? arg.toString() : JSON.toJSONString(arg);
                        sb.append("[").append(ClassUtils.getShortCanonicalName(arg.getClass())).append("]: ").append(value).append(";");
                    }
                }
            }
        } catch (Exception e) {
            log.error("WinSecuritySysLogAspect recordSysLog pre error", e);
        }
        Object ret = point.proceed();
        //无异常记录日志
        try {
            String className = ClassUtils.getShortCanonicalName(point.getTarget().getClass().getName());
            String methodName = point.getSignature().getName();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String requestUrl = request.getRequestURI();
            if (StringUtils.isNotBlank(request.getQueryString())) {
                requestUrl += "?" + request.getQueryString();
            }
            String optContent = String
                    .format("[类名]:%s,[方法]:%s,[请求]:%s,[数据]:%s", className, methodName, requestUrl, sb.toString());
            WinSecuritySysLogDTO sysLog = new WinSecuritySysLogDTO();
            if (winSecurityAccessService.isAuthenticated()) {
                WinSecurityBaseUserDTO userDTO = winSecurityAccessService.getLoginUserInfo();
                if (userDTO != null) {
                    sysLog.setUserName(userDTO.getUserName());
                }
                List<WinSecurityBaseRoleDTO> roleDTOList = winSecurityAccessService.getLoginUserRoleList();
                if (CollectionUtils.isNotEmpty(roleDTOList)) {
                    List<String> roleNameList = roleDTOList.stream().map(o -> o.getName()).collect(Collectors.toList());
                    sysLog.setRoleName(StringUtils.join(roleNameList, ","));
                }
            }
            sysLog.setOptContent(optContent);
            sysLog.setClientIp(HttpUtils.INSTANCE.getIpAddr(request));
            log.debug("WinSecurity SysLog add, data: {}", JSON.toJSONString(sysLog));
            winSecuritySysLogService.addSysLog(sysLog);
        } catch (Exception e) {
            log.error("WinSecuritySysLogAspect recordSysLog post error", e);
        }
        return ret;
    }


}
