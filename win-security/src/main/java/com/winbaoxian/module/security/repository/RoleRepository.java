package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByDeletedFalseOrderBySeqAsc();

    Page<RoleEntity> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);
}
