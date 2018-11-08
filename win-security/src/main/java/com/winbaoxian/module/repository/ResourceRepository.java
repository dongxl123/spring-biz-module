package com.winbaoxian.module.repository;

import com.winbaoxian.module.model.entity.ResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findAllByDeletedFalseOrderBySeqAsc();

    int countByDeletedFalse();

    Page<ResourceEntity> findAllByDeletedFalseOrderBySeqAsc(Pageable pageable);

}
