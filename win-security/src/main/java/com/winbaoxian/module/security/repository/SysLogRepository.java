package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.SysLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLogEntity, Long> {
}
