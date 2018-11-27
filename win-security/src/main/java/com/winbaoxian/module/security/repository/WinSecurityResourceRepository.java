package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityResourceRepository extends JpaRepository<WinSecurityResourceEntity, Long> {

    List<WinSecurityResourceEntity> findAllByDeletedFalse();

    boolean existsByCodeAndPidAndDeletedFalse(String code, Long pid);

    boolean existsByCodeAndPidAndIdNotAndDeletedFalse(String code, Long pid, Long Id);

    List<WinSecurityResourceEntity> findByPidAndDeletedFalse(Long pid);

    @Query("select a from WinSecurityResourceEntity a,WinSecurityRoleResourceEntity b,WinSecurityUserRoleEntity c, WinSecurityBaseUserEntity d, SecurityRoleEntity e WHERE a.id= b.resourceId and b.roleId = c.roleId and c.userId = d.id and b.roleId=e.id and d.userName=?1 and a.deleted=false and a.status=1 and e.status=1")
    List<WinSecurityResourceEntity> getValidResourceListByUserName(String userName);

    @Query("select a from WinSecurityResourceEntity a,WinSecurityRoleResourceEntity b,WinSecurityUserRoleEntity c,SecurityRoleEntity d WHERE a.id= b.resourceId and b.roleId = c.roleId and b.roleId=d.id and c.userId=?1 and a.deleted=false and a.status=1 and d.status=1")
    List<WinSecurityResourceEntity> getValidResourceListByUserId(Long userId);

}
