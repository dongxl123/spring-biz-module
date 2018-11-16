package com.winbaoxian.module.example.model.dto;

import com.winbaoxian.module.security.model.dto.BaseUserDTO;
import lombok.Data;

@Data
public class SecurityUserDTO extends BaseUserDTO {
    private String edu;
}
