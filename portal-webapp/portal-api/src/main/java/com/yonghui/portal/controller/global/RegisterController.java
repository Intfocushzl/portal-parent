package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.R;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/31
 * Description :供前端使用的注册接口
 */
@RestController
@RequestMapping(value = "register")
public class RegisterController {
    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserService userService;

    /**
     * 检测待注册用户工号是否真实存在
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    @IgnoreAuth
    public R getUserInfo(HttpServletResponse response, String userNum) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, Object> map = null;
        try {
            log.info("getUserInfo" + userNum);
            map = userService.getPersonnelMattersStatus(userNum);
            log.info("getUserInfo" + map);
        } catch (Exception e) {
            return R.error("查找用户名异常！");
        }
        return R.success(map);
    }

    /**
     * 注册
     */
    @RequestMapping("reg")
    public R reg(HttpSession session, HttpServletRequest request, User user, int district) {
        try {
            if (null != userService.getUserByJobNumber(user.getJobNumber())) {
                return R.error(2, "用户已注册，不能重复注册");
            }
            if ("全部".equals(user.getLargeArea())) {
                user.setLargeArea("ALL");
            }
            if ("全部".equals(user.getAreaMans())) {
                user.setAreaMans("ALL");
            }
            if ("全部".equals(user.getProvince())) {
                user.setProvince("ALL");
            }

            if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
                user.setStoreNumber("ALL");
            }

            user.setStatus(0);

            user.setAccount(user.getJobNumber().trim());
            user.setType(district);

            user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));

            int res = userService.insertSelective(user);

            if (res == 1) {
                //TODO: 2017/06/01 调用APP端的注册接口
                return R.success().setMsg("注册成功");
            }else {
                return R.error(0, "注册异常");
            }

        } catch (Exception e) {
            log.error("注册异常", e);
            return  R.error(0, "注册异常");
        }
    }



}

