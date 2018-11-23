package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityResourceRepository extends JpaRepository<WinSecurityResourceEntity, Long> {

    List<WinSecurityResourceEntity> findAllByDeletedFalse();

    boolean existsByCodeAndPidAndDeletedFalse(String code, Long pid);

    boolean existsByCodeAndPidAndIdNotAndDeletedFalse(String code, Long pid, Long Id);

    @Query("select a from WinSecurityResourceEntity a,WinSecurityRoleResourceEntity b,WinSecurityUserRoleEntity c, WinSecurityBaseUserEntity d WHERE a.id= b.resourceId and b.roleId = c.roleId and c.userId = d.id and d.userName=?1 and a.deleted=false")
    List<WinSecurityResourceEntity> getUserResourceList(String userName);
}
