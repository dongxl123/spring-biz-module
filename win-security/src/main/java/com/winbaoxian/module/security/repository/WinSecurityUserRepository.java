package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WinSecurityUserRepository extends JpaRepository<WinSecurityUserEntity, Long>, JpaSpecificationExecutor<WinSecurityUserEntity> {

    WinSecurityUserEntity findOneById(Long id);

    boolean existsByUserNameAndDeletedFalse(String userName);

    boolean existsByUserNameAndIdNotAndDeletedFalse(String userName, Long id);

    List<WinSecurityUserEntity> findAllByDeletedFalse();

    Page<WinSecurityUserEntity> findAllByDeletedFalse(Pageable pageable);

    WinSecurityUserEntity findOneByUserNameAndDeletedFalse(String userName);

    WinSecurityUserEntity findOneByMobileAndDeletedFalse(String mobile);

}
