package com.winbaoxian.module.security.pac4j;

import lombok.Getter;
import lombok.Setter;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.callback.QueryParameterCallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.util.CommonHelper;

@Getter
@Setter
public class WinQueryParameterCallbackUrlResolver extends QueryParameterCallbackUrlResolver {

    private String callbackParamName;

    @Override
    public String compute(final UrlResolver urlResolver, final String url, final String clientName, final WebContext context) {
        String newUrl = super.compute(urlResolver,url,clientName,context);
        newUrl = CommonHelper.addParameter(newUrl, callbackParamName, context.getRequestParameter(callbackParamName).orElse(""));
        return newUrl;
    }
}
