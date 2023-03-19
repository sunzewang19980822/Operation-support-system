package com.kfm.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    private static final String OPTIONS = "OPTIONS";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        System.out.println("路过");
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpServletRequest httpReq = (HttpServletRequest) request;

        httpResp.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        httpResp.setHeader("Access-Control-Allow-Origin", "*");
        httpResp.setHeader("Access-Control-Allow-Credentials","true");
        httpResp.setHeader("Access-Control-Max-Age", "86400");//预检缓存时间（秒）
        httpResp.setHeader("Access-Control-Allow-Headers", "Authorization");
        if (httpReq.getMethod().equalsIgnoreCase("OPTIONS")) {//预检
            httpResp.setHeader("Access-Control-Allow-Headers",
                    httpReq.getHeader("Access-Control-Request-Headers"));
            httpResp.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }

        chain.doFilter(request, response);
    }
}
