package com.winbaoxian.module.security.strategy;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public abstract class AbstractSecurityPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        String nameText = name.getText().toUpperCase();
        String tablePrefixText = tablePrefix().toUpperCase();
        Identifier newName = name;
        if (StringUtils.isNotBlank(tablePrefixText)) {
            for (String securityTableName : WinSecurityConstant.SECURITY_TABLE_ARRAY) {
                if (securityTableName.equals(nameText)) {
                    newName = new Identifier(tablePrefixText + "_" + name.getText(), name.isQuoted());
                    break;
                }
            }
        }
        return super.toPhysicalTableName(newName, jdbcEnvironment);
    }

    public abstract String tablePrefix();


}
