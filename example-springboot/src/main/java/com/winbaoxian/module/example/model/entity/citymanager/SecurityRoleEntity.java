package com.winbaoxian.module.example.model.entity.citymanager;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class SecurityRoleEntity extends WinSecurityBaseRoleEntity {

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

}
