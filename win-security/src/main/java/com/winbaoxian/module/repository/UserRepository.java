package com.winbaoxian.module.repository;

import com.winbaoxian.module.model.entity.BaseUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository<E extends BaseUserEntity> extends JpaRepository<E, Long> {

    boolean existsByUserNameAndDeletedFalse(String userName);

    boolean existsByUserNameAndIdNotAndDeletedFalse(String userName, Long id);

    List<E> findAllByDeletedFalse();

    Page<E> findAllByDeletedFalse(Pageable pageable);
}
