package com.winbaoxian.module.repository;

import com.winbaoxian.module.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    void deleteByUserId(Long userId);

    List<UserRoleEntity> findByUserId(Long userId);
}
