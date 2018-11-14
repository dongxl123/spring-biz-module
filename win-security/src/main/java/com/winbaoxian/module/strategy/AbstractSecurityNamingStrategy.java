package com.winbaoxian.module.strategy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public abstract class AbstractSecurityNamingStrategy extends SpringPhysicalNamingStrategy {

    public static final String[] SECURITY_TABLE_LIST = {"USER", "ROLE", "RESOURCE", "USER_ROLE", "ROLE_RESOURCE"};

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String nameText = name.getText().toUpperCase();
        String tablePrefixText = tablePrefix();
        if (StringUtils.isNotBlank(tablePrefixText)) {
            for (String securityTableName : SECURITY_TABLE_LIST) {
                if (securityTableName.equals(nameText)) {
                    return new Identifier(tablePrefixText + "_" + name.getText(), name.isQuoted());
                }
            }
        }
        return name;
    }

    public abstract String tablePrefix();

}
