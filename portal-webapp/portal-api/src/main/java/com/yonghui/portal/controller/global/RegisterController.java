package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/31
 * Description :
 */
@RestController
@RequestMapping(value="register")
public class RegisterController {
    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserService userService;


    /**
     *
     * */
    @RequestMapping("getUserName")
    @ResponseBody
    @IgnoreAuth
    public R getUserName(HttpServletResponse response, String userNum) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, Object> map = null;
        try {
            log.info("getUserName" + userNum);
            map = userService.getPersonnelMattersStatus(userNum);
            log.info("getUserName" + map);
        } catch (Exception e) {
            return R.error("查找用户名异常！");
        }
        return R.success(map);
    }


}

