package com.yonghui.portal.util;

import com.yonghui.portal.model.report.PortalOpenapiReport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 封装请求参数
     */
    public static String getRequestParameter(HttpServletRequest request) {
        Map params = request.getParameterMap();
        Iterator it = params.keySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            String paramName = (String) it.next();
            String paramValue = request.getParameter(paramName);
            //处理得到的参数名与值
            if (!"yongHuiReportCustomCode".equals(paramName)) {
                sb.append(paramName + "=" + paramValue + "@@");
            }
        }

        String str = sb.toString();
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return str.substring(0, str.length() - 2);
    }

    /**
     * 记录日志用，获取请求里面的全部参数
     *
     * @param request
     * @return
     */
    public static String getParameterForLog(HttpServletRequest request) {
        Map params = request.getParameterMap();
        Iterator it = params.keySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            String paramName = (String) it.next();
            String paramValue = request.getParameter(paramName);
            //处理得到的参数名与值
            sb.append(paramName + "=" + paramValue + "@@");
        }

        String str = sb.toString();
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return str.substring(0, str.length() - 2);
    }

    /**
     * 验证sign用，获取请求里面去掉sign全部参数
     *
     * @param request
     * @return
     */
    public static String getParameterForSign(HttpServletRequest request , PortalOpenapiReport portalOpenapiReport) throws Exception {
        String params = null;
        String method = request.getMethod();
        if ("GET".equals(method)) {
            params = URLDecoder.decode(request.getQueryString(),"utf-8");
            return params.substring(0, params.indexOf("sign=") - 1);
        } else if ("POST".equals(method)) {
            Map paramsMap = request.getParameterMap();
            Iterator it = paramsMap.keySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (it.hasNext()) {
                String paramName = (String) it.next();
                String paramValue = request.getParameter(paramName);
                //处理得到的参数名与值
                if (!"openApiCode".equals(paramName) && !"sign".equals(paramName)) {
                    sb.append(paramName + "=" + paramValue + "@@");
                }
            }
            String str = sb.toString();
            if (StringUtils.isEmpty(str)) {
                return params;
            }
           return StringUtils.getRouteParameter(str.substring(0, str.length() - 2),portalOpenapiReport.getParameter());
        }
        return params;
    }
}
