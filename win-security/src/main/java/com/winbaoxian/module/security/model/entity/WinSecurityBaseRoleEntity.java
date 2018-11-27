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
 * 角色(SECURITY_ROLE)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ROLE")
@Data
public class WinSecurityBaseRoleEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8255877523481723896L;

    /** 主键id */
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

    /** 角色名 */
    @Column(name = "NAME")
    private String name;

    /** 简介 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 排序号 */
    @Column(name = "SEQ")
    private Long seq;

    /** 状态, 0:无效 , 1:有效 */
    @Column(name = "STATUS")
    private Integer status;

    /**  */
    @Column(name = "DELETED")
    private Boolean deleted;

}