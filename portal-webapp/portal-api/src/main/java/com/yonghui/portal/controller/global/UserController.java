package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Author : 杨杨
 * Date : 2017/06/01
 * Description :供外部调用的注册接口
 */
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private SysoperationLogService sysoperationLogService;
    @Reference
    private UserService userService;

    public static final String KEY = "yhappQKXYfkjqn8Yq6ojACkwXRnt35322896dfd9419f9d2c4080b064d89a";

    /**
     * 注册
     */
    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public R regSecond(HttpSession session, HttpServletRequest request
            , String jobNumber
            , String userName
            , String email
            , String mobile
            , Integer district
            , Integer roleId
            , String password
            , String largeArea
            , String areaMans
            , String firm
            , String storeNumber
            , String remark
            , String sign) {
        try {

            //首先判断客户端秘钥是否正确
            String originSign = Md5Util.getMd5("MD5", 0, null, "APPRegister" + KEY);
            if (!originSign.equals(sign)) {
                return R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "sign验证失败");
            }
            //记录日志
            SysOperationLog log = new SysOperationLog();
            log.setStartTime(new Date());
            log.setIp(new IPUtils().getIpAddr(request));
            log.setUrl(request.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(request));
            log.setRemark("App注册");

            User user = new User();
            if (jobNumber == null || jobNumber.equals("")) {
                return R.error(1, "员工号不能为空");
            }
            if (null != userService.getUserByJobNumber(jobNumber)) {
                return R.error(2, "用户已注册，不能重复注册");
            } else {
                user.setJobNumber(jobNumber.trim());
            }
            user.setAccount(jobNumber.trim());

            if (userName != null && !userName.equals("")) {
                user.setName(userName);
            } else {
                return R.error(1, "用户名不能为空");
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (mobile != null && !mobile.equals("")) {
                user.setMobile(mobile);
            } else {
                return R.error(1, "手机号码不能为空");
            }

            user.setType(district);

            user.setRoleId(roleId);

            if (largeArea != null && "全部".equals(largeArea)) {
                user.setLargeArea("ALL");
            } else if (largeArea != null) {
                user.setLargeArea(largeArea);
            }
            if (areaMans != null && "全部".equals(areaMans)) {
                user.setAreaMans("ALL");
            } else if (areaMans != null) {
                user.setAreaMans(areaMans);
            }

            if (storeNumber == null || storeNumber.equals("")) {
                user.setStoreNumber("ALL");
            } else {
                user.setStoreNumber(storeNumber);
            }

            user.setStatus(0);

            if (password != null && !password.equals("")) {
                user.setPass(Md5Util.getMd5("MD5", 0, null, password));
            } else {
                return R.error(1, "密码不能为空");
            }

            if (firm == null || firm.equals("")) {
                user.setFirm("ALL");
            } else {
                user.setFirm(firm);
            }

            if (remark != null) {
                user.setRemark(remark);
            }

            int res = userService.insertSelective(user);

            if (res == 1) {
                log.setEndTime(new Date());
                sysoperationLogService.SaveLog(log);
                return R.success().setMsg("注册成功");
            } else {
                log.setEndTime(new Date());
                sysoperationLogService.SaveLog(log);
                return R.error(0, "注册异常");
            }

        } catch (Exception e) {
            log.error("注册异常", e);
            return R.error(0, "注册异常");
        }
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    private R updatePassword(HttpSession session, HttpServletRequest request
            , String jobNumber
            , String password
            , String sign) {
        //首先判断客户端秘钥是否正确
        String originSign = Md5Util.getMd5("MD5", 0, null, "APPUpdatePassword" + KEY);
        if (!originSign.equals(sign)) {
            return R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "sign验证失败");
        }
        //记录日志
        SysOperationLog log = new SysOperationLog();
        log.setStartTime(new Date());
        log.setIp(new IPUtils().getIpAddr(request));
        log.setUrl(request.getRequestURL().toString());
        log.setParameter(HttpContextUtils.getParameterForLog(request));
        log.setRemark("App修改或重置密码");
        int res = userService.updatePasswordByJobNumber(jobNumber, password);

        log.setEndTime(new Date());
        sysoperationLogService.SaveLog(log);
        if (res == 1) {
            return R.success().setMsg("修改密码成功");
        } else {
            return R.error(0, "修改密码异常");
        }
    }

}
