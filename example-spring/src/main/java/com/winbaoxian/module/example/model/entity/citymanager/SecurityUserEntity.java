package com.winbaoxian.module.example.model.entity.citymanager;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
public class SecurityUserEntity extends WinSecurityBaseUserEntity {

    @Column(name = "EDU")
    private String edu;

}
