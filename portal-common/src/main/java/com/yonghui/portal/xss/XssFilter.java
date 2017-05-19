package com.yonghui.portal.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 * 拦截防止sql注入、xss注入
 *
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) request);

        chain.doFilter(xssRequest, response);
    }

    public void destroy() {
    }

}