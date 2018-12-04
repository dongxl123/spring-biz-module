package com.winbaoxian.module.security.service.extension;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
interface IProcessor<D, E> {

    /**
     * 1执行业务逻辑前处理
     *
     * @param dto
     */
    void preProcess(D dto);

    /**
     * 2 执行sql前处理
     *
     * @param entity
     */
    void preSqlProcess(E entity);


    /**
     * 3 执行业务逻辑后处理
     *
     * @param dto
     */
    void postProcess(D dto);
}
