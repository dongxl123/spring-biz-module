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

/**
 * 资源(SECURITY_RESOURCE)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Data
public class WinSecurityResourceDTO implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4311421266482800320L;

    /** 主键 */
    private Long id;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 资源名称 */
    private String name;

    /** 编码 */
    private String code;

    /** 全局编码, 格式: 系统code.菜单code.thisCode */
    private String globalCode;

    /** 资源路径 */
    private String value;

    /** 资源介绍 */
    private String description;

    /** 资源图标 */
    private String icon;

    /** 父级资源id */
    private Long pid;

    /** 排序 */
    private Long seq;

    /** 状态，0:无效 , 1:有效 */
    private Integer status;

    /** 资源类别, 0:无特别作用，1:菜单，2:其他 */
    private Integer resourceType;

    /**
     * 后端接口地址，支持多个，中间用,隔开
     */
    private String ajaxUrls;

    /**
     * 属于哪些角色拥有，中间用,隔开， NULL不限制
     */
    private String belongRoles;

    private Boolean deleted;

}