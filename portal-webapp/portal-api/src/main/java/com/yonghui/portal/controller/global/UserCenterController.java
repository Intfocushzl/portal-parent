package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.RRException;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

    public static final String APP_BASE_URL = "http://yonghui-test.idata.mobi/api/v2/user/";

    @Reference
    private UserService userService;

    /*获取个人中心的信息*/
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
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

    /*修改信息*/
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public R updateUserInfo(@RequestParam Map<String, Object> params) {

        try {
            String jobNumber = params.get("jobNumber") == null ? "" : params.get("jobNumber").toString();
            String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
            String userName = params.get("userName") == null ? "" : params.get("userName").toString();
            String email = params.get("email") == null ? "" : params.get("email").toString();

            if (!StringUtils.isMobile(mobile)){
                log.info(mobile);
                return R.error().setMsg("手机号码格式不正确");
            }
            if (!StringUtils.isEmail(email)){
                log.info(email);
                return R.error().setMsg("邮箱地址格式不正确");
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("api_token","api_token");
            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("user_name",userName);
            userMap.put("mobile",mobile);
            userMap.put("email",email);

            map.put("user",userMap );
            System.out.println(JSON.json(map));
            int res = userService.updateInfoByJobNumber(params);
            if (res == 1) {
                //TODO: 2017/06/01 调用APP端的修改用户信息接口
                    HttpMethodUtil httpUtil = new HttpMethodUtil();
                    try {
                        String result = httpUtil.getPostJsonResult(APP_BASE_URL+jobNumber, JSON.json(map));
                        System.out.println(result);
                        if (!StringUtils.isEmpty(result)) {
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            if (jsonObject.getInteger("code") == 201) {
                                log.info(jsonObject.toJSONString());
                                return R.success().setMsg(jsonObject.getString("message"));
                            } else if (jsonObject.getInteger("code") == 200||jsonObject.getInteger("code") == 401) {
                                log.error(jsonObject.toJSONString());
                                return R.error().setMsg(jsonObject.getString("message"));
                            } else {
                                log.error(jsonObject.toJSONString());
                                return R.error().setMsg("APP用户信息同步失败");
                            }
                        }else {
                            return R.error().setMsg("用户信息修改失败");
                        }
                    } catch (Exception e) {
                        throw new RRException("调用外部系统出错：" + APP_BASE_URL + "异常信息为：" + e.getMessage());
                    }
            } else {
                return R.error().setMsg("用户信息修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("用户信息修改异常");
        }
    }

    /*修改密码*/
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    private R updatePassword(HttpSession session, HttpServletRequest request, String jobNumber, String oldPassword, String newPassword) {
        String oldPasswordMD5 = Md5Util.getMd5("MD5", 0, null, oldPassword);
        String newPasswordMD5 = Md5Util.getMd5("MD5", 0, null, newPassword);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("api_token","api_token");
        map.put("password", newPasswordMD5);
        try {
            System.out.println(JSON.json(map));
            User user = userService.Login(jobNumber, oldPasswordMD5);
            if (user != null) {
                int res = userService.updatePasswordByJobNumber(jobNumber, newPasswordMD5);
                if (res == 1) {
                    //TODO: 2017/06/01 调用APP端的修改密码接口
                    HttpMethodUtil httpUtil = new HttpMethodUtil();
                    try {
                        String result = httpUtil.getPostResult(APP_BASE_URL+jobNumber+"/password", map);
                        if (!StringUtils.isEmpty(result)) {
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            if (jsonObject.getInteger("code") == 201) {
                                log.info(jsonObject.toJSONString());
                                return R.success().setMsg(jsonObject.getString("message"));
                            } else if (jsonObject.getInteger("code") == 200||jsonObject.getInteger("code") == 401) {
                                log.error(jsonObject.toJSONString());
                                return R.error().setMsg(jsonObject.getString("message"));
                            } else {
                                log.error(jsonObject.toJSONString());
                                return R.error().setMsg("APP密码同步失败");
                            }
                        }
                    } catch (Exception e) {
                        throw new RRException("调用外部系统出错：" + APP_BASE_URL + "异常信息为：" + e.getMessage());
                    }
                } else {
                    return R.error().setMsg("修改密码异常");
                }
            } else {
                return R.error().setMsg("账号或密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("修改密码异常");
        }
        return R.error().setMsg("修改密码失败");
    }
    /*获取随机密码*/
    @RequestMapping(value = "getNewPassword", method = RequestMethod.GET)
    private R getNewPassword(String jobNumber, String mobile) {
        //TODO: 2017/06/05 调用APP端获取随机密码接口
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("api_token","api_token");
        map.put("mobile", mobile);
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        try {
            String result = httpUtil.getPostJsonResult(APP_BASE_URL+jobNumber+"/reset_password", JSON.json(map));
            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    log.info(jsonObject.toJSONString());
                    return R.success().setMsg(jsonObject.getString("message"));
                } else if (jsonObject.getInteger("code") == 200||jsonObject.getInteger("code") == 401) {
                    log.error(jsonObject.toJSONString());
                    return R.error().setMsg(jsonObject.getString("message"));
                } else {
                    log.error(jsonObject.toJSONString());
                    return R.error().setMsg("获取随机密码失败");
                }
            }else {
                return R.error().setMsg("获取随机密码失败");
            }
        } catch (Exception e) {
         (new RRException("调用外部系统出错：" + APP_BASE_URL + "异常信息为：" + e.getMessage())).printStackTrace();
            return R.error().setMsg("获取随机密码异常");
        }
    }

    /*重置密码、忘记密码*/
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    private R resetPassword(String jobNumber, String password) {
        int res = userService.updatePasswordByJobNumber(jobNumber, password);

        if (res == 1) {
            //TODO: 2017/06/05 调用APP端获取随机密码接口
            return R.success().setMsg("重置密码成功");
        } else {
            return R.error().setMsg("重置密码异常");
        }
    }

    /*修改信息*/
    @RequestMapping(value = "changeGrant", method = RequestMethod.POST)
    public R changeGrant(HttpSession session, HttpServletRequest request,
                         String jobNumber,
                         String changeRoleId,
                         String changeLargeArea,
                         String changeAreaMans,
                         String changeStoreNumber,
                         String changeFirm,
                         String remark) {

        try {
            if (StringUtils.isEmpty(jobNumber)) {
                return R.error().setMsg("员工号不能为空");
            }
            User user = new User();
            user.setJobNumber(jobNumber);
            User retUser = userService.getUserByJobNumber(jobNumber);
            if (retUser == null) {
                return R.error().setMsg("该员工号未注册");
            }
            if (changeLargeArea.equals("全部")) {
                user.setChangeLargeArea("ALL");
            }
            if (changeAreaMans.equals("全部")) {
                user.setChangeAreaMans("ALL");
            }
            if (changeStoreNumber.equals("全部")) {
                user.setStoreNumber("ALL");
            }
            user.setFirm(changeFirm);
            user.setChangeRoleId(changeRoleId);
            user.setId(retUser.getId());
            user.setChangeStatus(1);
            user.setRemark(remark);

            int res = userService.changeGrant(user);
            if (res == 1) {
                return R.success().setMsg("权限变更提交成功");
            } else {
                return R.error().setMsg("权限变更提交失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("权限变更提交异常");
        }
    }

}
