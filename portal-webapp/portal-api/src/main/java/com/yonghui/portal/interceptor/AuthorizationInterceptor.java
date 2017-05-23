package com.yonghui.portal.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.api.TokenApi;
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


/**
 * api总线系统鉴权拦截
 * 权限(Token)验证
 *
 * @date 张海 2017-05-11
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String LOGIN_USER_JOB_NUMBER = "LOGIN_USER_JOB_NUMBER";

    public static final String LOGIN_USER_OPERATION_LOG= "LOGIN_USER_OPERATION_LOG";

    @Reference
    private UserService userService;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        // 如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }
        // 从header中获取token
        String token = request.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)) {
            response.getWriter().write(JSON.toJSONString(ConstantsUtil.ExceptionCode.TO_LOGIN));
            return false;
        }
        // 从redis中查询token信息
        String tokenJsonStr = redisBizUtilApi.getApiToken(token);
        TokenApi tokenApi = null;
        if (StringUtils.isBlank(tokenJsonStr)) {
            response.getWriter().write(JSON.toJSONString(ConstantsUtil.ExceptionCode.TO_LOGIN));
            return false;
        } else {
            tokenApi = JSONObject.parseObject(tokenJsonStr, TokenApi.class);
            if (tokenApi == null || tokenApi.getExpireTime().getTime() < System.currentTimeMillis()) {
                response.getWriter().write(JSON.toJSONString(ConstantsUtil.ExceptionCode.TO_LOGIN));
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
        request.setAttribute(LOGIN_USER_OPERATION_LOG,log);
        return true;
    }


}