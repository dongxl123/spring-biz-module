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
 * 角色资源(SECURITY_ROLE_RESOURCE)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ROLE_RESOURCE")
@Data
public class WinSecurityRoleResourceEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4294916243343047672L;

    /** 主键id */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** 角色id */
    @Column(name = "ROLE_ID")
    private Long roleId;

    /** 资源id */
    @Column(name = "RESOURCE_ID")
    private Long resourceId;

}