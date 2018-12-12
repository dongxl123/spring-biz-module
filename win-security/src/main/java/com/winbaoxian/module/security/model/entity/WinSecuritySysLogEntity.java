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

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志(SECURITY_SYS_LOG)
 * 
 * @author dongxuanliang252
 * @version 1.0.0 2018-11-08
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SYS_LOG")
@Data
public class WinSecuritySysLogEntity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1844554441598762316L;

    /** 主键id */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    @CreationTimestamp
    private Date createTime;

    /** 登陆名 */
    @Column(name = "USER_NAME")
    private String userName;

    /** 角色名 */
    @Column(name = "ROLE_NAME")
    private String roleName;

    /** 内容 */
    @Column(name = "OPT_CONTENT")
    private String optContent;

    /** 客户端ip */
    @Column(name = "CLIENT_IP")
    private String clientIp;

}