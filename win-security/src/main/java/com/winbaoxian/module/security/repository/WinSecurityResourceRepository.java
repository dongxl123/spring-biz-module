package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityResourceRepository extends JpaRepository<WinSecurityResourceEntity, Long>, JpaSpecificationExecutor<WinSecurityResourceEntity> {

    WinSecurityResourceEntity findOneByAppCodeAndId(String appCode, Long id);

    List<WinSecurityResourceEntity> findAllByAppCodeAndStatusAndDeletedFalseOrderBySeqAsc(String appCode, Integer status);

    boolean existsByAppCodeAndCodeAndPidAndDeletedFalse(String appCode, String code, Long pid);

    boolean existsByAppCodeAndCodeAndPidAndIdNotAndDeletedFalse(String appCode, String code, Long pid, Long id);

    List<WinSecurityResourceEntity> findByAppCodeAndPidAndDeletedFalse(String appCode, Long pid);

    @Query("select distinct a from WinSecurityResourceEntity a,WinSecurityRoleResourceEntity b,WinSecurityUserRoleEntity c,WinSecurityRoleEntity d WHERE a.id= b.resourceId and b.roleId = c.roleId and b.roleId=d.id and a.appCode=?1 and c.userId=?2 and a.deleted=false and a.status=1 and d.status=1 order by a.seq asc ")
    List<WinSecurityResourceEntity> getValidResourceListByUserId(String appCode, Long userId);

    List<WinSecurityResourceEntity> findByAppCodeAndPidAndDeletedFalseOrderBySeqAscIdAsc(String appCode, Long pid);

}
