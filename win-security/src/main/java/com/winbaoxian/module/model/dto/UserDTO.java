package com.winbaoxian.module.model.dto;/*
 * Welcome to use the TableGo Tools.
 *
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 *
 * Author:dongxuanliang252
 * Email:edinsker@163.com
 * Version:5.0.0
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户(SECURITY_USER)
 *
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Data
public class UserDTO implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9002339217989625310L;

    /** 主键id */
    private Long id;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 登陆名 */
    private String userName;

    /** 用户名 */
    private String name;

    /** 手机号 */
    private String mobile;

    /** 用户状态，, 0:禁用 , 1:启用, 2:全部放行 */
    private Integer status;

    /** 关联的权限 */
    private List<Long> roleIdList;

}