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
    private Integer seq;

    /** 状态，0：有效  1：失效 */
    private Integer status;

    /** 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量 */
    private Integer resourceType;


}