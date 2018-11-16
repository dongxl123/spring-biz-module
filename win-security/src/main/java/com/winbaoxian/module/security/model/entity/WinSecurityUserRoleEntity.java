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

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

/**
 * 用户角色(SECURITY_USER_ROLE)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "USER_ROLE")
@Data
public class WinSecurityUserRoleEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -529712275466926177L;

    /** 主键id */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** 用户id */
    @Column(name = "USER_ID")
    private Long userId;

    /** 角色id */
    @Column(name = "ROLE_ID")
    private Long roleId;

}