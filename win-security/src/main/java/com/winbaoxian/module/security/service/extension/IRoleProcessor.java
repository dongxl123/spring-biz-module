package com.winbaoxian.module.security.service.extension;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 19:40
 */
interface IRoleProcessor<D extends WinSecurityBaseRoleDTO, E extends WinSecurityBaseRoleEntity> extends IProcessor<D, E> {

}
