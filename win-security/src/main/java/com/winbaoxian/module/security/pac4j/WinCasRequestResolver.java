package com.winbaoxian.module.security.pac4j;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.http.*;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.redirect.RedirectionActionBuilder;
import org.pac4j.core.util.CommonHelper;

import java.util.Optional;

import static org.pac4j.core.context.HttpConstants.FACES_PARTIAL_AJAX_PARAMETER;

public class WinCasRequestResolver implements AjaxRequestResolver {

    @Override
    public boolean isAjax(WebContext context) {
        Optional<String> ticket = context.getRequestParameter("ticket");
        return !ticket.isPresent();
    }

    @Override
    public HttpAction buildAjaxResponse(WebContext context, RedirectionActionBuilder redirectionActionBuilder) {
        String url = null;
        final RedirectionAction action = redirectionActionBuilder.getRedirectionAction(context).orElse(null);
        if (action instanceof WithLocationAction) {
            url = ((WithLocationAction) action).getLocation();
        }

        if (!context.getRequestParameter(FACES_PARTIAL_AJAX_PARAMETER).isPresent()) {
            if (CommonHelper.isNotBlank(url)) {
//                        context.setResponseHeader(HttpConstants.LOCATION_HEADER, url);
                context.setResponseHeader("REQUIRES_AUTH", "1");
                context.setResponseHeader("REQUIRES_AUTH_URL", url);
            }
            throw UnauthorizedAction.INSTANCE;
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
        buffer.append("<partial-response>");
        if (CommonHelper.isNotBlank(url)) {
            buffer.append("<redirect url=\"" + url.replaceAll("&", "&amp;") + "\"></redirect>");
        }
        buffer.append("</partial-response>");

        return RedirectionActionHelper.buildFormPostContentAction(context, buffer.toString());
    }
}
