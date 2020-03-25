package com.winbaoxian.module.security.pac4j;

import lombok.Getter;
import lombok.Setter;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasAuthenticator;

@Getter
@Setter
public class WinCasClient extends CasClient {

    private String callbackParam = "callback";

    public WinCasClient(CasConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void clientInit(){
        WinQueryParameterCallbackUrlResolver parameterCallbackUrlResolver = new WinQueryParameterCallbackUrlResolver();
        parameterCallbackUrlResolver.setCallbackParamName(callbackParam);
        this.defaultAuthenticator(new CasAuthenticator(this.getConfiguration(), this.getName(), this.getUrlResolver(), parameterCallbackUrlResolver, this.callbackUrl));
        super.clientInit();
        setCallbackUrlResolver(parameterCallbackUrlResolver);

    }
}
