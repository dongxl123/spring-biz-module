package com.winbaoxian.module.repository;

import com.winbaoxian.module.model.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<ResourceEntity, Long> {
}
