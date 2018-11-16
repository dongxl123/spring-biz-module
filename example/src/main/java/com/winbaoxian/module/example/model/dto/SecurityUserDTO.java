package com.winbaoxian.module.example.model.dto;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import lombok.Data;

@Data
public class SecurityUserDTO extends WinSecurityBaseUserDTO {
    private String edu;
}
