package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WinSecurityUserRepository<E extends WinSecurityBaseUserEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    boolean existsByUserNameAndDeletedFalse(String userName);

    boolean existsByUserNameAndIdNotAndDeletedFalse(String userName, Long id);

    List<E> findAllByDeletedFalse();

    Page<E> findAllByDeletedFalse(Pageable pageable);

    E findOneByUserNameAndDeletedFalse(String userName);

    E findOneByMobileAndDeletedFalse(String mobile);

}
