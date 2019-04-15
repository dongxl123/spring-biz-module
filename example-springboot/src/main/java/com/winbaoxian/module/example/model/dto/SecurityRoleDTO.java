package com.winbaoxian.module.example.model.dto;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecurityRoleDTO extends WinSecurityBaseRoleDTO {
    private Long departmentId;
}
