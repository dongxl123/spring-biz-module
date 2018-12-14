package com.winbaoxian.module.security.config.definition;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.annotation.EnableWinSecurity;
import com.winbaoxian.module.security.config.ControllerEffectiveScope;
import com.winbaoxian.module.security.config.EnableWinSecurityAttributeEnum;
import com.winbaoxian.module.security.controller.WinSecurityAccessController;
import com.winbaoxian.module.security.controller.WinSecurityResourceController;
import com.winbaoxian.module.security.controller.WinSecurityRoleController;
import com.winbaoxian.module.security.controller.WinSecurityUserController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dongxuanliang252
 * @date 2018-12-05 10:39
 */
public class ControllerEffectiveScopeSelector implements ImportSelector {

    private static final Logger log = LoggerFactory.getLogger(ControllerEffectiveScopeSelector.class);
    private static final String[] NO_IMPORTS = {};

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        Map<String, Object> annotationAttributesMap = annotationMetadata
                .getAnnotationAttributes(EnableWinSecurity.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributesMap);
        ControllerEffectiveScope[] scopes = (ControllerEffectiveScope[]) annotationAttributes.get(EnableWinSecurityAttributeEnum.CONTROLLER_SCOPES.getValue());
        if (ArrayUtils.isEmpty(scopes)) {
            scopes = new ControllerEffectiveScope[]{ControllerEffectiveScope.NONE};
        }
        boolean hasAll = false;
        boolean hasNone = false;
        for (ControllerEffectiveScope scope : scopes) {
            if (ControllerEffectiveScope.NONE.equals(scope)) {
                hasNone = true;
            }
            if (ControllerEffectiveScope.ALL.equals(scope)) {
                hasAll = true;
            }
        }
        String[] imports = NO_IMPORTS;
        if (hasNone) {
            return NO_IMPORTS;
        } else if (hasAll) {
            imports = new String[]{
                    WinSecurityUserController.class.getName(),
                    WinSecurityRoleController.class.getName(),
                    WinSecurityResourceController.class.getName(),
                    WinSecurityAccessController.class.getName()
            };
        } else {
            List<String> importList = new ArrayList<>();
            for (ControllerEffectiveScope scope : scopes) {
                String className = null;
                if (ControllerEffectiveScope.USER.equals(scope)) {
                    className = WinSecurityUserController.class.getName();
                } else if (ControllerEffectiveScope.ROLE.equals(scope)) {
                    className = WinSecurityRoleController.class.getName();
                } else if (ControllerEffectiveScope.RESOURCE.equals(scope)) {
                    className = WinSecurityResourceController.class.getName();
                } else if (ControllerEffectiveScope.ACCESS.equals(scope)) {
                    className = WinSecurityAccessController.class.getName();
                }
                if (StringUtils.isNotBlank(className)) {
                    importList.add(className);
                }
            }
            imports = importList.toArray(new String[importList.size()]);
        }
        log.info("WinSecurity: ControllerEffectiveScopeSelector, (Bean){} is selected", JSON.toJSONString(imports));
        return imports;
    }

}
