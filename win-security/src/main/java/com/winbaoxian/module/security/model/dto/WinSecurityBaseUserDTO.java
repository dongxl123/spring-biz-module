package com.winbaoxian.module.security.model.dto;/*
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

import com.winbaoxian.module.security.annotation.SearchParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用户(SECURITY_USER)
 *
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Data
public class WinSecurityBaseUserDTO implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9002339217989625310L;

    /** 主键id */
    private Long id;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 登陆名 */
    @SearchParam(compare= SearchParam.COMPARE.like)
    private String userName;

    /** 用户名 */
    @SearchParam(compare= SearchParam.COMPARE.like)
    private String name;

    /** 手机号 */
    @SearchParam(compare= SearchParam.COMPARE.like)
    private String mobile;

    /** 用户状态，, 0:无效 , 1:有效 */
    private Integer status;

    /** 关联的权限 */
    private Set<Long> roleIdList;

    /** 是否是超级管理员 */
    private Boolean superAdminFlag;

    private Boolean deleted;

}