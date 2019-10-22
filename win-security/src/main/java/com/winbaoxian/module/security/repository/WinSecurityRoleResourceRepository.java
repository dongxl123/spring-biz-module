package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityRoleResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinSecurityRoleResourceRepository extends JpaRepository<WinSecurityRoleResourceEntity, Long> {

    void deleteByRoleId(Long roleId);

    List<WinSecurityRoleResourceEntity> findByRoleId(Long roleId);
}
