package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityResourceRepository extends JpaRepository<WinSecurityResourceEntity, Long>, JpaSpecificationExecutor<WinSecurityResourceEntity> {

    List<WinSecurityResourceEntity> findAllByDeletedFalse();

    List<WinSecurityResourceEntity> findAllByStatusAndDeletedFalse(Integer status);

    boolean existsByCodeAndPidAndDeletedFalse(String code, Long pid);

    boolean existsByCodeAndPidAndIdNotAndDeletedFalse(String code, Long pid, Long Id);

    List<WinSecurityResourceEntity> findByPidAndDeletedFalse(Long pid);

    @Query("select a from WinSecurityResourceEntity a,WinSecurityRoleResourceEntity b,WinSecurityUserRoleEntity c,WinSecurityBaseRoleEntity d WHERE a.id= b.resourceId and b.roleId = c.roleId and b.roleId=d.id and c.userId=?1 and a.deleted=false and a.status=1 and d.status=1")
    List<WinSecurityResourceEntity> getValidResourceListByUserId(Long userId);

}
