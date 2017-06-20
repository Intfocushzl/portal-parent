package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/06/01
 * Description :供外部调用的注册接口
 */
@RestController
@RequestMapping(value = "app/api/user")
public class UserController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private SysoperationLogService sysoperationLogService;
    @Reference
    private UserService userService;

    @OpenAuth
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    private R updatePassword(HttpSession session, HttpServletRequest request, @RequestParam
            Map<String, Object> params) {
        //记录日志
        SysOperationLog log = new SysOperationLog();
        log.setStartTime(new Date());
        log.setIp(new IPUtils().getIpAddr(request));
        log.setUrl(request.getRequestURL().toString());
        log.setParameter(HttpContextUtils.getParameterForLog(request));
        log.setRemark("App修改或重置密码");

        String jobNumber = params.get("jobNumber") == null ? "" : params.get("jobNumber").toString();
        String password = params.get("password") == null ? "" : params.get("password").toString();
        if (StringUtils.isEmpty(jobNumber)) {
            return R.error().setMsg("工号不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return R.error().setMsg("密码不能为空");
        }
        int res = userService.updatePasswordByJobNumber(jobNumber, password);

        log.setEndTime(new Date());
        sysoperationLogService.SaveLog(log);
        if (res == 1) {
            return R.success().setMsg("修改密码成功");
        } else {
            return R.error().setMsg("修改密码异常");
        }
    }

    @OpenAuth
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    private R updateUserInfo(HttpSession session, HttpServletRequest request, @RequestParam
            Map<String, Object> params) {
        //记录日志
        SysOperationLog log = new SysOperationLog();
        log.setStartTime(new Date());
        log.setIp(new IPUtils().getIpAddr(request));
        log.setUrl(request.getRequestURL().toString());
        log.setParameter(HttpContextUtils.getParameterForLog(request));
        log.setRemark("App修改用户信息");

        int res = userService.updateInfoByJobNumber(params);

        log.setEndTime(new Date());
        sysoperationLogService.SaveLog(log);
        if (res == 1) {
            return R.success().setMsg("修改用户信息成功");
        } else {
            return R.error().setMsg("修改用户信息失败");
        }
    }

}
