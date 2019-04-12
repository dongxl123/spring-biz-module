package com.winbaoxian.module.example.model.dto;

import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecurityUserDTO extends WinSecurityBaseUserDTO {
    private String edu;
    private String fillEdu;
}
