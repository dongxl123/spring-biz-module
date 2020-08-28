package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WinSecurityUserRepository extends JpaRepository<WinSecurityUserEntity, Long>, JpaSpecificationExecutor<WinSecurityUserEntity> {

    WinSecurityUserEntity findOneByAppCodeAndId(String appCode, Long id);

    boolean existsByAppCodeAndUserNameAndDeletedFalse(String appCode, String userName);

    boolean existsByAppCodeAndUserNameAndIdNotAndDeletedFalse(String appCode, String userName, Long id);

    WinSecurityUserEntity findOneByAppCodeAndUserNameAndDeletedFalse(String appCode, String userName);

    WinSecurityUserEntity findOneByAppCodeAndMobileAndDeletedFalse(String appCode, String mobile);

}
