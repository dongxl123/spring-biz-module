package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinSecurityRoleRepository extends JpaRepository<WinSecurityRoleEntity, Long> {

    List<WinSecurityRoleEntity> findAllByDeletedFalseOrderBySeqAsc();

    Page<WinSecurityRoleEntity> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);
}
