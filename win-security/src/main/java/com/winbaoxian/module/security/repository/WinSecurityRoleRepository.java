package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityRoleRepository extends JpaRepository<WinSecurityRoleEntity, Long>, JpaSpecificationExecutor<WinSecurityRoleEntity> {

    WinSecurityRoleEntity findOneByAppCodeAndId(String appCode, Long id);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and a.appCode=?1 and b.userId =?2 and a.deleted=false")
    List<WinSecurityRoleEntity> getRoleListByUserId(String appCode, Long userId);

    WinSecurityRoleEntity findByAppCodeAndNameAndDeletedFalse(String appCode, String name);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and a.appCode=?1 and b.userId =?2 and a.deleted=false")
    WinSecurityRoleEntity getRoleByUserId(String appCode, Long userId);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and a.appCode=?1 and b.userId =?2 and a.deleted=false and a.status = 1")
    List<WinSecurityRoleEntity> getValidRoleListByUserId(String appCode, Long userId);

}
