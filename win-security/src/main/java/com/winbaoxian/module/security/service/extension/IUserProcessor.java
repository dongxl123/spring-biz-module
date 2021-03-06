package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
interface IUserProcessor<D extends WinSecurityBaseUserDTO, E extends WinSecurityBaseUserEntity> extends IProcessor<D, E> {

}
