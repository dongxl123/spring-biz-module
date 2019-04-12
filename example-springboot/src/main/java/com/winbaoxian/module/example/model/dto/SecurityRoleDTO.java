package com.winbaoxian.module.example.model.dto;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import lombok.Data;

@Data
public class SecurityRoleDTO extends WinSecurityBaseRoleDTO {
    private Long departmentId;
}
