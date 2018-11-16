package com.winbaoxian.module.example.component.security;

import com.winbaoxian.module.security.strategy.AbstractSecurityPhysicalNamingStrategy;

public class SecurityPhysicalNamingStrategy extends AbstractSecurityPhysicalNamingStrategy {

    @Override
    public String tablePrefix() {
        return "SECURITY";
    }
}
