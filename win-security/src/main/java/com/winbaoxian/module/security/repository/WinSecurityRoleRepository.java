package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinSecurityRoleRepository<E extends WinSecurityBaseRoleEntity> extends JpaRepository<E, Long> {

    List<E> findAllByDeletedFalseOrderBySeqAsc();

    Page<E> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);
}
