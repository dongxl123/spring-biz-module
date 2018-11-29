package com.winbaoxian.module.security.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-11-29 14:49
 */
@Data
public class WinSecurityUserAllInfoDTO implements Serializable {

    private WinSecurityBaseUserDTO userInfo;
    private List<WinSecurityBaseRoleDTO> roleList;
    private List<WinSecurityResourceDTO> resourceList;

}
