package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.RoleResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResourceEntity, Long> {

    void deleteByRoleId(Long roleId);

    List<RoleResourceEntity> findByRoleId(Long roleId);
}
