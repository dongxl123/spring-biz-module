package com.winbaoxian.module.security.pac4j;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.DefaultCallbackLogic;
import org.pac4j.core.exception.http.*;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class WinCasCallbackLogic<R, C extends WebContext> extends DefaultCallbackLogic<R,C> {

    @Override
    public R perform(final C context, final Config config, final HttpActionAdapter<R, C> httpActionAdapter,
                     final String inputDefaultUrl, final Boolean inputSaveInSession, final Boolean inputMultiProfile,
                     final Boolean inputRenewSession, final String client) {
        //根据参数设置回调页面
        Optional<String> callbackUrl = context.getRequestParameter("callback");
        if(callbackUrl.isPresent()){
            context.getSessionStore().set(context, Pac4jConstants.REQUESTED_URL, new FoundAction(callbackUrl.get()));
        }
        return super.perform(context,config,httpActionAdapter,inputDefaultUrl,inputSaveInSession,inputMultiProfile,inputRenewSession,client);
    }

    @Override
    protected R handleException(final Exception e, final HttpActionAdapter<R, C> httpActionAdapter, final C context){
        if(httpActionAdapter!=null&&context!=null){

            if(e instanceof UnknownAccountException||e instanceof DisabledAccountException){
                final HttpAction action = ForbiddenAction.INSTANCE;
                logger.debug("extra HTTP action required in security: {}", action.getCode());
                return httpActionAdapter.adapt(action, context);
            }else if (e instanceof AuthorizationException){
                final HttpAction action = UnauthorizedAction.INSTANCE;
                logger.debug("extra HTTP action required in security: {}", action.getCode());
                return httpActionAdapter.adapt(action, context);
            }
        }
        return super.handleException(e,httpActionAdapter,context);
    }
}
