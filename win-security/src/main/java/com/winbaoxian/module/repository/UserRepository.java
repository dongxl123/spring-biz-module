package com.winbaoxian.module.repository;

import com.winbaoxian.module.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    List<UserEntity> findAllByDeletedFalse();

    Page<UserEntity> findAllByDeletedFalse(Pageable pageable);
}
