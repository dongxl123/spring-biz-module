package com.winbaoxian.module.security.model.dto;

import com.winbaoxian.module.security.model.enums.LoginType;
import lombok.Data;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 10:15
 */
@Data
public class WinSecurityPrincipal {

    private Long id;
    private String userName;
    private LoginType loginType;

}
