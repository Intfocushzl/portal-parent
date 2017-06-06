package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/06/06
 * Description :
 */
@RestController
@RequestMapping(value = "user/info")
public class UserCenterController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserService userService;

    @RequestMapping(value = "getUserInformation", method = RequestMethod.GET)
    public R getUserInformation(String jobNumber) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            User user = userService.getUserByJobNumber(jobNumber);
            result.put("userInfo", user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.success(result);
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.GET)
    public R updateUserInfo(String jobNumber,String userName,String mobile,String email) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("jobNumber",jobNumber);
        params.put("userName",userName);
        params.put("mobile",mobile);
        params.put("email",email);

        try {
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.success();
    }
}
