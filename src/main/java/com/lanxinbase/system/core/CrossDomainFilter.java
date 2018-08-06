package com.lanxinbase.system.core;

import com.lanxinbase.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by alan.luo on 2017/9/5.
 */
public class CrossDomainFilter implements Filter {

    private final Logger logger = Logger.getLogger(CrossDomainFilter.class.getName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 跨域
            String origin = httpRequest.getHeader("Origin");
            if (origin == null) {
                origin = "http://localhost";
            }

//            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+origin);
//
//            if (!origin.contains(Constant.CDF) && !origin.contains("test.com") && !origin.contains("localhost")){
//                throw new Exception("Unauthorized");
//            }


            httpResponse.addHeader("Access-Control-Allow-Origin", origin);
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type,X-Response-Code-By, Accept,X-Cookie, access-control-allow-origin,"+ Constant.tokenKey);
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
            if ( httpRequest.getMethod().equals("OPTIONS") ) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }
}
