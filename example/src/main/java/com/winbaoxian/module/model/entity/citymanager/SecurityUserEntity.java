package com.winbaoxian.module.model.entity.citymanager;

import com.winbaoxian.module.model.entity.BaseUserEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class SecurityUserEntity extends BaseUserEntity {

    @Column(name = "EDU")
    private String edu;

}
