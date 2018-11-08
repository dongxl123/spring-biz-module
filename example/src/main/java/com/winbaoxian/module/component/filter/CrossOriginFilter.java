package com.winbaoxian.module.component.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author DongXL
 * @Create 2016-12-05 18:09
 */

@WebFilter(filterName = "crossOriginFilter", urlPatterns = "/*")
public class CrossOriginFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("CrossOriginFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("CrossOriginFilter doFilter start");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String curOrigin = httpRequest.getHeader("Origin");
        if (curOrigin != null) {
            logger.debug("当前访问来源是：" + curOrigin);
        }
        // 如果当前访问来源在application.properties的Access-Control-Allow-Origin配置范围内，则允许访问，否则不允许
        List<String> regexOrigins = Arrays.asList("http(s)?://(.*)\\.winbaoxian\\.(.*)", "http(s)?://192\\.168\\.(.*)");
        if (curOrigin != null) {
            for (String regexOrigin : regexOrigins) {
                if (curOrigin.matches(regexOrigin)) {
                    httpResponse.setHeader("Access-Control-Allow-Origin", curOrigin);
                    break;
                }
            }
        } else { // 对于无来源的请求(比如在浏览器地址栏直接输入请求的)，那就只允许我们自己的机器可以吧
            httpResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1");
        }
        httpResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, GET");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Headers", "reqid, nid, host, x-real-ip, x-forwarded-ip, event-type, event-id, accept, content-type");
        chain.doFilter(request, response);
        logger.debug("CrossOriginFilter doFilter end");
    }

    @Override
    public void destroy() {
        logger.debug("CrossOriginFilter destroy");
    }
}
