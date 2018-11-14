package com.winbaoxian.module.component.security;

import com.winbaoxian.module.strategy.AbstractSecurityNamingStrategy;

public class SecurityNamingStrategy extends AbstractSecurityNamingStrategy {

    @Override
    public String tablePrefix() {
        return "SECURITY";
    }
}
