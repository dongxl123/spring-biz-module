package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecuritySysLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinSecuritySysLogRepository extends JpaRepository<WinSecuritySysLogEntity, Long> {
}
