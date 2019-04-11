package com.winbaoxian.module.cas.strategy;

import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lidong on 17/8/1.
 */
public class AjaxAuthRedirectStrategy implements AuthenticationRedirectStrategy {
    @Override
    public void redirect(HttpServletRequest request, HttpServletResponse response, String potentialRedirectUrl) throws IOException {
        response.setStatus(401);
        response.setIntHeader("REQUIRES_AUTH",1);
        response.setHeader("REQUIRES_AUTH_URL", potentialRedirectUrl);
    }
}
