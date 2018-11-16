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

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色(SECURITY_ROLE)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Data
public class WinSecurityRoleDTO implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8255877523481723896L;

    /** 主键id */
    private Long id;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 角色名 */
    private String name;

    /** 简介 */
    private String description;

    /** 排序号 */
    private Integer seq;

    /** 状态, 0:无效 , 1:有效 */
    private Integer status;

    /** 关联的资源 */
    private List<Long> resourceIdList;

}