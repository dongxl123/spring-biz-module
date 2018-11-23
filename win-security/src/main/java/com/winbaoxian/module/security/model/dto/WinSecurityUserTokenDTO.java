package com.winbaoxian.module.security.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongxuanliang252
 * @date 2018-11-23 10:00
 */
@Data
public class WinSecurityUserTokenDTO implements Serializable {

    private String userName;
    private String password;

}
