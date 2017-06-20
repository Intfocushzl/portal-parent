package com.yonghui.portal.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.model.report.PortalOpenapiReport;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.util.redis.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * api总线系统鉴权拦截
 * 权限(Token)验证
 *
 * @date 张海 2017-05-11
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER_JOB_NUMBER = "LOGIN_USER_JOB_NUMBER";

    public static final String LOGIN_USER_OPERATION_LOG = "LOGIN_USER_OPERATION_LOG";

    @Reference
    private UserService userService;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth annotation;
        OpenAuth openAuthAnnotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            openAuthAnnotation = ((HandlerMethod) handler).getMethodAnnotation(OpenAuth.class);
        } else {
            return true;
        }
        // 如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }
        //如果有@OpenAuth注解，则校验sign
        if (openAuthAnnotation != null) {
            Map paramsMap = request.getParameterMap();
            String openApiCode = request.getParameter("openApiCode");
            String sign = request.getParameter("sign");
            if (StringUtils.isBlank(openApiCode) || StringUtils.isBlank(sign)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "sign或者code不能为空")));
                return false;
            }
            // 从redis中查询key信息
            String openApiJsonStr = redisBizUtilApi.getPortalOpenApiReport(openApiCode);
            PortalOpenapiReport portalOpenapiReport = null;
            if (StringUtils.isBlank(openApiJsonStr)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "sign不存在")));
                return false;
            } else {
                portalOpenapiReport = JSONObject.parseObject(openApiJsonStr, PortalOpenapiReport.class);
                if (portalOpenapiReport == null || portalOpenapiReport.getKey() == null || portalOpenapiReport.getCode() == null) {
                    response.setHeader("Content-type", "text/html;charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "sign不存在")));
                    return false;
                } else {
                    //获取请求参数，并转成这种格式“shppID=9318&barcode=2304348000004”,参数为空也要写成“shppID=”这种
                    String parameter = null;
                    try {
                         parameter = HttpContextUtils.getParameterForSign(request,portalOpenapiReport);
                    } catch (Exception e) {
                        response.setHeader("Content-type", "text/html;charset=UTF-8");
                        response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "sign不能为空")));
                        return false;
                    }
                    if (StringUtils.isBlank(parameter)) {
                        response.setHeader("Content-type", "text/html;charset=UTF-8");
                        response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "请求参数不能为空")));
                        return false;
                    }
                    Md5Util util = new Md5Util();
                    //md5加密字符串为：key + parameter + key
                    String originSign = util.getMd5("MD5", 0, null, portalOpenapiReport.getKey() + parameter + portalOpenapiReport.getKey());
                    if (!originSign.equals(sign)) {
                        response.setHeader("Content-type", "text/html;charset=UTF-8");
                        response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.SIGN_ERROR, "sign验证失败")));
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        // 从header中获取token
        String token = request.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "token不能为空,请尝试登录")));
            return false;
        }
        // 从redis中查询token信息
        String tokenJsonStr = redisBizUtilApi.getApiToken(token);
        TokenApi tokenApi = null;
        if (StringUtils.isBlank(tokenJsonStr)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "token不存在，请尝试登录")));
            return false;
        } else {
            tokenApi = JSONObject.parseObject(tokenJsonStr, TokenApi.class);
            if (tokenApi == null || tokenApi.getExpireTime().getTime() < System.currentTimeMillis()) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "token已失效，重新登录")));
                return false;
            }
        }
        //设置账户到request里，后续根据jobNumber，获取用户信息
        request.setAttribute(LOGIN_USER_JOB_NUMBER, tokenApi.getJobNumber());
        //设置用户操作日志对象
        SysOperationLog log = new SysOperationLog();
        log.setJobNumber(tokenApi.getJobNumber());
        log.setStartTime(new Date());
        //获取用户ip,url.参数
        IPUtils iputil = new IPUtils();
        log.setIp(iputil.getIpAddr(request));
        log.setUrl(request.getRequestURL().toString());
        log.setParameter(HttpContextUtils.getRequestParameter(request));
        request.setAttribute(LOGIN_USER_OPERATION_LOG, log);
        return true;
    }


}