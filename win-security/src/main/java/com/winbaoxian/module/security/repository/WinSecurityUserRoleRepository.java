package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinSecurityUserRoleRepository extends JpaRepository<WinSecurityUserRoleEntity, Long> {

    void deleteByUserId(Long userId);

    List<WinSecurityUserRoleEntity> findByUserId(Long userId);

    List<WinSecurityUserRoleEntity> findByUserIdIn(List<Long> userIdList);

}
