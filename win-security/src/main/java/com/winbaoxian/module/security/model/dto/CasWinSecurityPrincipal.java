package com.winbaoxian.module.security.model.dto;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.pac4j.core.profile.CommonProfile;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-12-03 10:15
 */
@Getter
@Setter
public class CasWinSecurityPrincipal extends Pac4jPrincipal {

    public CasWinSecurityPrincipal(List<CommonProfile> profiles, String principalNameAttribute,Long id,String userName){
        super(profiles,principalNameAttribute);
        this.id = id;
        this.userName = userName;
    }

    private Long id;
    private String userName;

}
