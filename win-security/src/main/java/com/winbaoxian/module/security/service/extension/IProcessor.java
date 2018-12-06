package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.exceptions.WinSecurityException;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
public interface IProcessor<D, E> {

    /**
     * 执行业务逻辑前处理
     *
     * @param params 前端参数
     * @throws WinSecurityException
     */
    void preProcess(D params) throws WinSecurityException;


    /**
     * 业务验证逻辑
     *
     * @param params
     * @throws WinSecurityException
     */
    void customValidateAfterCommon(D params) throws WinSecurityException;

    /**
     * 执行sql前处理, dto到entity映射特殊处理
     *
     * @param params 前端参数
     * @param entity 最新entity
     * @throws WinSecurityException
     */
    void customMappingAfterCommon(D params, E entity) throws WinSecurityException;


    /**
     * 执行业务逻辑后处理
     *
     * @param result 返回结果
     * @throws WinSecurityException
     */
    void postProcess(D result) throws WinSecurityException;
}
