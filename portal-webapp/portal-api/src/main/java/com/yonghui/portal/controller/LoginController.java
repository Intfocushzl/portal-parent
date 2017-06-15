package com.yonghui.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.TokenApiService;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.util.redis.ReportUtil;
import com.yonghui.portal.util.redis.TokenUtil;
import com.yonghui.portal.xss.SQLFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.yonghui.portal.interceptor.AuthorizationInterceptor.LOGIN_USER_OPERATION_LOG;

/**
 * 测试用户登陆
 * <p>
 * 张海 2017.05.13
 */
@RestController
public class LoginController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserService userService;
    @Reference
    private TokenApiService tokenApiService;
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private ReportUtil reportUtil;

    /**
     * 用户登陆操作
     *
     * @param request
     * @param password
     * @param jobnumber
     * @return TokenApi 只返回必要字段，不得返回用户密码和敏感信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(HttpServletRequest request, String password, String jobnumber) {
        try {
            if (jobnumber != null) {
                //设置用户操作日志对象
                SysOperationLog logInfo = new SysOperationLog();
                logInfo.setJobNumber(jobnumber);
                logInfo.setStartTime(new Date());
                //根据jobnumber查询用户
                User user = userService.getUserByJobNumber(jobnumber);
                if (user == null) {
                    return R.error("该用户暂未注册");
                }
                if (!StringUtils.isEmpty(password)) {
                    //验证密码（登陆操作）
                    String pass = Md5Util.getMd5("MD5", 0, null, password);
                    user = userService.Login(jobnumber, pass);
                    if (user == null) {
                        return R.error("账号或密码错误");
                    } else {
                        if (0 == user.getStatus()) {
                            return R.error("您的账号还没有被激活");
                        }
                        if (-2 == user.getStatus()) {
                            return R.error("该人员已经离职");
                        }
                    }
                    //生成token
                    TokenApi tokenApiLast = tokenApiService.queryByJobNumber(user.getJobNumber());
                    TokenApi tokenApiNew = tokenUtil.createToken(tokenApiLast, user);

                    logInfo.setRemark("login");
                    //获取用户ip,url.参数
                    IPUtils iputil = new IPUtils();
                    logInfo.setIp(iputil.getIpAddr(request));
                    logInfo.setUrl(request.getRequestURL().toString());
                    logInfo.setParameter(HttpContextUtils.getParameterForLog(request));
                    logInfo.setEndTime(new Date());
                    sysoperationLogService.SaveLog(logInfo);
                    log.info("登陆日志：" + "门店号＝" + user.getStoreNumber() + "工号＝" + user.getJobNumber() + "姓名＝"
                            + user.getName() + "token＝" + tokenApiNew.getToken());
                    return R.success(tokenApiNew).put("user", user);
                } else {
                    return R.error("密码为空");
                }
            } else {
                return R.error("jobnumber为空");
            }
        } catch (Exception e) {
            log.error("登陆异常", e);
            return R.error("登录错误");
        }
    }

    /**
     * 用户登出
     *
     * @param request
     */
    @RequestMapping(value = "/loginout", method = RequestMethod.POST)
    public void loginout(HttpServletRequest request, String token) {
        //先从header里面找，在找request，最后参数
        if (StringUtils.isEmpty(token)) {
            // 从header中获取token

            token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                // 从参数中获取token
                token = request.getParameter("token");
            }
        }
        if (!StringUtils.isEmpty(token)) {
            // 删除用token信息
            //    redisBizUtilApi.removeApiToken(token);
            //      tokenApiService.deleteByToken(token);
        }
    }

    /**
     * 获取帆软用户的密码
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/queryFinePwd", method = RequestMethod.GET)
public R queryFineUserPwd(HttpServletRequest req, HttpServletResponse response){
    String parameter = null;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String originPasssword = "";
    SysOperationLog log = (SysOperationLog) req.getAttribute(LOGIN_USER_OPERATION_LOG);
    try {
        parameter = HttpContextUtils.getRequestParameter(req);
        IPUtils iputil = new IPUtils();
        log.setIp(iputil.getIpAddr(req));
        log.setUrl(req.getRequestURL().toString());
        log.setParameter(HttpContextUtils.getParameterForLog(req));
        list = reportUtil.jdbcProListResultListMapByParam("REP_000055", SQLFilter.sqlInject(parameter));
        for(Map<String, Object> item : list){
            originPasssword = item.get("password").toString();
        }
        FineDecodePwdUtil util = new FineDecodePwdUtil();
        log.setEndTime(new Date());
        log.setRemark("queryFinePwd");
        sysoperationLogService.SaveLog(log);
        if(originPasssword == null || originPasssword == ""){
            return R.error("该用户不存在");
        }else{
         return R.success(util.passwordDecode(originPasssword));
        }
    } catch (Exception e) {
       return R.error("获取帆软用户的密码异常");
    }
}

    /**
     * 测试登陆产生token信息
     * http://localhost:8080/login
     *
     * @return
     */
    @IgnoreAuth
    @RequestMapping(value = "/loginTest")
    public R loginTest() {
        User user = new User();
        user.setJobNumber("CS123456");
        user.setName("测试");
        user.setRoleId(1);
        TokenApi tokenApiLast = tokenApiService.queryByJobNumber(user.getJobNumber());
        TokenApi tokenApiNew = tokenUtil.createToken(tokenApiLast, user);

        return R.success(tokenApiNew).put("last", tokenApiLast);
    }


    /**
     * token测试
     *
     * @param request
     */
    @IgnoreAuth
    @RequestMapping(value = "tokenTest")
    public R tokenTest(HttpServletRequest request) {
        User user = new User();
        user.setJobNumber("CS123456");
        user.setName("测试");
        user.setRoleId(1);
        //生成token
        TokenApi tokenApiNew = tokenUtil.createToken(null, user);
        return R.success(tokenApiNew);
    }

}
