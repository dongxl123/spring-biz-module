package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinSecurityRoleRepository<E extends WinSecurityBaseRoleEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    List<E> findAllByDeletedFalseOrderBySeqAsc();

    Page<E> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);

    @Query("select a from WinSecurityBaseRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false")
    List<E> getRoleListByUserId(Long userId);

    E findByNameAndDeletedFalse(String name);

    @Query("select a from WinSecurityBaseRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false")
    E getRoleByUserId(Long userId);

    @Query("select a from WinSecurityBaseRoleEntity a,WinSecurityUserRoleEntity b WHERE a.id= b.roleId and b.userId =?1 and a.deleted=false and a.status = 1")
    List<E> getValidRoleListByUserId(Long userId);

}
