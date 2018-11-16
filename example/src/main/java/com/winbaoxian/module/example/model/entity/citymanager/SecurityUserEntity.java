package com.winbaoxian.module.example.model.entity.citymanager;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class SecurityUserEntity extends WinSecurityBaseUserEntity {

    @Column(name = "EDU")
    private String edu;

}
