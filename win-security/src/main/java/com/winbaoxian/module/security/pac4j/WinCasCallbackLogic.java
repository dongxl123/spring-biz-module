package com.winbaoxian.module.security.pac4j;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.DefaultCallbackLogic;
import org.pac4j.core.exception.http.FoundAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.util.Pac4jConstants;

import java.util.Optional;

public class WinCasCallbackLogic<R, C extends WebContext> extends DefaultCallbackLogic<R,C> {

    @Override
    public R perform(final C context, final Config config, final HttpActionAdapter<R, C> httpActionAdapter,
                     final String inputDefaultUrl, final Boolean inputSaveInSession, final Boolean inputMultiProfile,
                     final Boolean inputRenewSession, final String client) {
        Optional<String> callbackUrl = context.getRequestParameter("callback");
        if(callbackUrl.isPresent()){
            context.getSessionStore().set(context, Pac4jConstants.REQUESTED_URL, new FoundAction(callbackUrl.get()));
        }
        return super.perform(context,config,httpActionAdapter,inputDefaultUrl,inputSaveInSession,inputMultiProfile,inputRenewSession,client);
    }
}
