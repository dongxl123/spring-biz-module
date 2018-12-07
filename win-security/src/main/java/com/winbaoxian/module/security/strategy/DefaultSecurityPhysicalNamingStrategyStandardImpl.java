package com.winbaoxian.module.security.strategy;

import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import com.winbaoxian.module.security.config.AnnotationAttributesHolder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.Assert;

/**
 * @author dongxuanliang252
 * @date 2018-12-06 17:43
 */
public class DefaultSecurityPhysicalNamingStrategyStandardImpl extends AbstractSecurityPhysicalNamingStrategyStandardImpl {

    @Override
    public String tablePrefix() {
        AnnotationAttributes enableWinSecurity = AnnotationAttributesHolder.INSTANCE.getEnableWinSecurity();
        Assert.notNull(enableWinSecurity, "DefaultSecurityPhysicalNamingStrategyStandardImpl.tablePrefix, @EnableWinSecurity can not get attributes");
        return enableWinSecurity.getString(EnableWinSecurityAttributeEnum.TABLE_PREFIX.getValue());
    }
}
