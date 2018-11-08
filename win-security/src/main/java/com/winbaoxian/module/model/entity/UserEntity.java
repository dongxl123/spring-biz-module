package com.winbaoxian.module.model.entity;/*
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 用户(SECURITY_USER)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "USER")
@Data
public class UserEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9002339217989625310L;

    /** 主键id */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 登陆名 */
    @Column(name = "USER_NAME")
    private String userName;

    /** 用户名 */
    @Column(name = "NAME")
    private String name;

    /** 手机号 */
    @Column(name = "MOBILE")
    private String mobile;

    /** 用户状态，, 0:禁用 , 1:启用, 2:全部放行 */
    @Column(name = "STATUS")
    private Integer status;

    /**  */
    @Column(name = "DELETED")
    private Boolean deleted;

}