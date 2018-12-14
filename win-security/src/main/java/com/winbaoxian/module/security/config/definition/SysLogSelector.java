package com.winbaoxian.module.security.config.definition;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import com.winbaoxian.module.security.config.log.WinSecuritySysLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-12 14:11
 */
public class SysLogSelector implements ImportSelector {

    private static final Logger log = LoggerFactory.getLogger(SysLogSelector.class);
    private static final String[] NO_IMPORTS = {};

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        Map<String, Object> annotationAttributesMap = annotationMetadata
                .getAnnotationAttributes(EnableWinSecurity.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributesMap);
        boolean sysLogFlag = annotationAttributes.getBoolean(EnableWinSecurityAttributeEnum.SYS_LOG.getValue());
        String[] imports = NO_IMPORTS;
        if (sysLogFlag) {
            imports = new String[]{WinSecuritySysLogAspect.class.getName()};
        }
        log.info("WinSecurity: SysLogSelector, (Bean){} is selected", JSON.toJSONString(imports));
        return imports;
    }
}
