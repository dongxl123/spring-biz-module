package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinSecurityUserRepository<E extends WinSecurityBaseUserEntity> extends JpaRepository<E, Long> {

    boolean existsByUserNameAndDeletedFalse(String userName);

    boolean existsByUserNameAndIdNotAndDeletedFalse(String userName, Long id);

    List<E> findAllByDeletedFalse();

    Page<E> findAllByDeletedFalse(Pageable pageable);

    E findOneByUserNameAndDeletedFalse(String userName);
}
