package com.winbaoxian.module.security.pac4j;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.matching.checker.DefaultMatchingChecker;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.matching.matcher.csrf.CsrfTokenGeneratorMatcher;
import org.pac4j.core.matching.matcher.csrf.DefaultCsrfTokenGenerator;

import java.util.Map;

public class WinCasMatchingChecker extends DefaultMatchingChecker {

    static final CsrfTokenGeneratorMatcher CSRF_TOKEN_MATCHER = new CsrfTokenGeneratorMatcher(new DefaultCsrfTokenGenerator());

    public WinCasMatchingChecker(String domain){
        CSRF_TOKEN_MATCHER.setDomain(domain);
    }

    @Override
    public boolean matches(final WebContext context, final String matchersValue, final Map<String, Matcher> matchersMap) {
        if(super.matches(context,matchersValue,matchersMap)){
            return CSRF_TOKEN_MATCHER.matches(context);
        }
        return false;
    }
}
