package com.winbaoxian.module.security.model.entity;/*
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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 资源(SECURITY_RESOURCE)
 *
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "RESOURCE")
@Data
public class WinSecurityResourceEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4311421266482800320L;

    /** 主键 */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Date createTime;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    @UpdateTimestamp
    private Date updateTime;

    /** 资源名称 */
    @Column(name = "NAME")
    private String name;

    /** 编码 */
    @Column(name = "CODE")
    private String code;

    /** 全局编码, 格式: 系统code.菜单code.thisCode */
    @Column(name = "GLOBAL_CODE")
    private String globalCode;

    /** 资源路径 */
    @Column(name = "VALUE")
    private String value;

    /** 资源介绍 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 资源图标 */
    @Column(name = "ICON")
    private String icon;

    /** 父级资源id */
    @Column(name = "PID")
    private Long pid;

    /** 排序 */
    @Column(name = "SEQ")
    private Long seq;

    /** 状态，0:无效 , 1:有效 */
    @Column(name = "STATUS")
    private Integer status;

    /** 资源类别, 0:无特别作用，1:菜单，2:其他 */
    @Column(name = "RESOURCE_TYPE")
    private Integer resourceType;

    /**
     * 后端接口地址，支持多个，中间用,隔开
     */
    @Column(name = "AJAX_URLS")
    private String ajaxUrls;

    /** 属于哪些角色拥有，中间用,隔开， NULL不限制 */
    @Column(name = "BELONG_ROLES")
    private String belongRoles;

    /**  */
    @Column(name = "DELETED")
    private Boolean deleted;

}