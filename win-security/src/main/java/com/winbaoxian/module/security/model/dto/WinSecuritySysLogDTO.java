package com.winbaoxian.module.security.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongxuanliang252
 * @date 2018-12-12 11:38
 */
@Data
public class WinSecuritySysLogDTO implements Serializable {

    /** 主键id */
    private Long id;

    /** 创建时间 */
    private Date createTime;

    /** 登陆名 */
    private String userName;

    /** 角色名 */
    private String roleName;

    /** 内容 */
    private String optContent;

    /** 客户端ip */
    private String clientIp;
}
