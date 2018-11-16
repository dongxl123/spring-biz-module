package com.winbaoxian.module.component.security;

import com.winbaoxian.module.strategy.AbstractSecurityPhysicalNamingStrategy;

public class SecurityPhysicalNamingStrategy extends AbstractSecurityPhysicalNamingStrategy {

    @Override
    public String tablePrefix() {
        return "SECURITY";
    }
}
