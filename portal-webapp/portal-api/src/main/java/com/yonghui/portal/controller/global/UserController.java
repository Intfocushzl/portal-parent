package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    private UserService userService;

    /**
     * 注册
     */
    @RequestMapping("reg")
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
            , String remark) {
        try {
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
                return R.success().setMsg("注册成功");
            } else {
                return R.error(0, "注册异常");
            }

        } catch (Exception e) {
            log.error("注册异常", e);
            return R.error(0, "注册异常");
        }
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    private R updatePassword(HttpSession session, HttpServletRequest request,
                             String jobNumber,
                             String password) {
        int res = userService.updatePasswordByJobNumber(jobNumber, password);

        if (res == 1) {
            return R.success().setMsg("修改密码成功");
        } else {
            return R.error(0, "修改密码异常");
        }
    }

}
