/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.winbaoxian.module.security.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * This filter validates the CAS service ticket to authenticate the user.  It must be configured on the URL recognized
 * by the CAS server.  For example, in {@code shiro.ini}:
 * <pre>
 * [main]
 * casFilter = org.apache.shiro.cas.CasFilter
 * ...
 *
 * [urls]
 * /shiro-cas = casFilter
 * ...
 * </pre>
 * (example : http://host:port/mycontextpath/shiro-cas)
 *
 * @see <a href="https://github.com/bujiio/buji-pac4j">buji-pac4j</a>
 * @since 1.2
 */
public class WinCasFilter extends PathMatchingFilter {

    private static Logger logger = LoggerFactory.getLogger(WinCasFilter.class);

    // the name of the parameter service ticket in url
    private static final String TICKET_PARAMETER = "ticket";

    // the url where the application is redirected if the CAS service ticket validation failed (example : /mycontextpatch/cas_error.jsp)
    private String failureUrl;

    /**
     * The token created for this authentication is a CasToken containing the CAS service ticket received on the CAS service url (on which
     * the filter must be configured).
     *
     * @param request  the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Principal principal = httpRequest.getUserPrincipal();
        if (principal != null && principal instanceof AttributePrincipal) {
            return new UsernamePasswordToken(principal.getName(), (String) null);
        }
        return null;
    }

    /**
     * 尝试登录
     *
     * @param request
     * @param response
     * @return
     */
    private boolean tryLogin(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            AuthenticationToken token = createToken(request, response);
            if (token != null) {
                subject.login(token);
            }
        } catch (AuthenticationException e) {
            logger.error("cas tryLogin failed", e);
        }
        return true;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return tryLogin(request, response);
    }
}
