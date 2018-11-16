package com.winbaoxian.module.strategy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public abstract class AbstractSecurityPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

    public static final String[] SECURITY_TABLE_LIST = {"USER", "ROLE", "RESOURCE", "USER_ROLE", "ROLE_RESOURCE"};

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        String nameText = name.getText().toUpperCase();
        String tablePrefixText = tablePrefix().toUpperCase();
        Identifier newName = name;
        if (StringUtils.isNotBlank(tablePrefixText)) {
            for (String securityTableName : SECURITY_TABLE_LIST) {
                if (securityTableName.equals(nameText)) {
                    newName = new Identifier(tablePrefixText + "_" + name.getText(), name.isQuoted());
                    break;
                }
            }
        }
        if (isUnderscoreRequired()) {
            return super.toPhysicalTableName(newName, jdbcEnvironment);
        } else {
            return newName;
        }
    }

    public abstract String tablePrefix();

    public boolean isUnderscoreRequired() {
        return true;
    }


}
