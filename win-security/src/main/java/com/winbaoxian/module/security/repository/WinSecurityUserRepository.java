package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WinSecurityUserRepository<E extends WinSecurityBaseUserEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    E findOneByIdAndAppId(Long id, Long appId);

    boolean existsByUserNameAndAppIdAndDeletedFalse(String userName, Long appId);

    boolean existsByUserNameAndIdNotAndAppIdAndDeletedFalse(String userName, Long id, Long appId);

    E findOneByUserNameAndAppIdAndDeletedFalse(String userName, Long appId);

    E findOneByMobileAndAppIdAndDeletedFalse(String mobile, Long appId);

}
