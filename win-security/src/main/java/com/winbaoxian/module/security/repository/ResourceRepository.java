package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.ResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findAllByDeletedFalseOrderBySeqAsc();

    Page<ResourceEntity> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);

    boolean existsByCodeAndPidAndDeletedFalse(String code, Long pid);

    boolean existsByCodeAndPidAndIdNotAndDeletedFalse(String code, Long pid, Long Id);

}
