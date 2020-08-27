package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityRoleRepository extends JpaRepository<WinSecurityRoleEntity, Long>, JpaSpecificationExecutor<WinSecurityRoleEntity> {

    WinSecurityRoleEntity findOneById(Long id);

    List<WinSecurityRoleEntity> findAllByDeletedFalseOrderBySeqAsc();

    Page<WinSecurityRoleEntity> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false")
    List<WinSecurityRoleEntity> getRoleListByUserId(Long userId);

    WinSecurityRoleEntity findByNameAndDeletedFalse(String name);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false")
    WinSecurityRoleEntity getRoleByUserId(Long userId);

    @Query("select a from WinSecurityRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false and a.status = 1")
    List<WinSecurityRoleEntity> getValidRoleListByUserId(Long userId);

}
