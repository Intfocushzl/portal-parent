package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.RRException;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/31
 * Description :供前端使用的注册接口
 */
@RestController
@RequestMapping(value = "user/register")
public class RegisterController {
    Logger log = Logger.getLogger(this.getClass());

    public static final String APP_BASE_URL = "http://yonghui-test.idata.mobi/api/v2/user";

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
            if (userNum == null) {
                return R.error("传参异常");
            }
            log.info("getUserInfo:userNum" + userNum);
            if (!userNum.substring(0, 6).equals("601933")) {
                map = userService.getPersonnelMattersStatus(userNum);
            } else {
                map = new HashedMap();
                map.put("employee_no", userNum);
                map.put("employee_name", "测试" + userNum);
            }
            log.info("getUserInfo:map" + map);
            if (map == null) {
                return R.error("员工号错误");
            }
        } catch (Exception e) {
            return R.error("查找用户名异常！");
        }
        return R.success(map);
    }

    /**
     * 注册
     */
    @RequestMapping(value = "reg", method = RequestMethod.POST)
    @IgnoreAuth
    public R reg(String jobNumber,
                 String userName,
                 String mobile,
                 String email,
                 String roleId,
                 String largeArea,
                 String areaMans,
                 String storeNumber,
                 String firm,
                 String remark,
                 String pass) {
        try {
            User user = new User();
            if (null != userService.getUserByJobNumber(jobNumber)) {
                //return R.error(1, "用户已注册，不能重复注册");
            } else {
                user.setJobNumber(jobNumber);
                user.setAccount(jobNumber);
            }
            if (StringUtils.isEmpty(userName)) {
                return R.error("用户名不能为空");
            } else {
                user.setName(userName);
            }
            if (StringUtils.isEmpty(mobile)) {
                return R.error("手机号不能为空");
            } else {
                user.setMobile(mobile);
            }
            if (StringUtils.isEmpty(roleId)) {
                return R.error("角色不能为空");
            } else {
                user.setRoleId(roleId);
            }
            if (StringUtils.isEmpty(firm)) {
                return R.error("商行不能为空");
            } else {
                if ("全部".equals(firm)) {
                    user.setFirm("ALL");
                } else {
                    user.setFirm(firm);
                }
            }
            if (email != null) {
                user.setEmail(email);
            }
            if (remark != null) {
                user.setRemark(remark);
            }
            if (StringUtils.isEmpty(largeArea)) {
                return R.error("大区不能为空");
            } else {
                if ("全部".equals(largeArea)) {
                    user.setLargeArea("ALL");
                } else {
                    user.setLargeArea(largeArea);
                }
            }
            if (StringUtils.isEmpty(areaMans)) {
                return R.error("片区不能为空");
            } else {
                if ("全部".equals(areaMans)) {
                    user.setAreaMans("ALL");
                } else {
                    user.setAreaMans(areaMans);
                }
            }
            if (StringUtils.isEmpty(storeNumber)) {
                return R.error("门店不能为空");
            } else {
                if ("全部".equals(storeNumber)) {
                    user.setStoreNumber("ALL");
                } else {
                    user.setStoreNumber(storeNumber);
                }
            }

            user.setStatus(0);

            user.setType(1);

            user.setPass(Md5Util.getMd5("MD5", 0, null, pass));

            int res = userService.insertSelective(user);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("api_token", "api_token");
            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("user_num", jobNumber);
            userMap.put("user_name", userName);
            userMap.put("user_pass", Md5Util.getMd5("MD5", 0, null, pass));
            userMap.put("mobile", mobile);
            userMap.put("email", email);
            map.put("user", userMap);
            System.out.println(JSON.json(map));
            if (res == 1) {
//                if (res == 1) {
//                    //TODO: 2017/06/01 调用APP端的创建用户信息接口
//                    HttpMethodUtil httpUtil = new HttpMethodUtil();
//                    try {
//                        String result = "{\"code\":201,\"data\":{\"user_num\":\"60193302\",\"user_name\":\"懒羊羊\",\"mobile\":\"15555555555\",\"created_at\":\"2017-06-14T19:58:27.000+08:00\",\"tel\":\"\",\"id\":100221,\"email\":\"\",\"user_pass\":\"e10adc3949ba59abbe56e057f20f883e\",\"status\":true},\"message\":\"创建用户成功\"} ";//httpUtil.getPostJsonResult(APP_BASE_URL, JSON.json(map));
//                        System.out.println(result);
//                        if (!StringUtils.isEmpty(result)) {
//                            JSONObject jsonObject = JSONObject.parseObject(result);
//                            if (jsonObject.getInteger("code") == 201) {
//                                log.info(jsonObject.toJSONString());
//                                Map<String, Object> newMap = new HashMap<String, Object>();
//                                newMap.put("api_token", "api_token");
//                                newMap.put("role_ids", roleId.split(","));
//                                result = httpUtil.getPostJsonResult(APP_BASE_URL+"/"+jobNumber+"/roles", JSON.json(newMap));
//                                if(!StringUtils.isEmpty(result)) {
//                                    jsonObject = JSONObject.parseObject(result);
//                                    if (jsonObject.getInteger("code") == 201) {
//                                        return R.success().setMsg(jsonObject.getString("message"));
//                                    }else {
//                                        return R.success().setMsg(jsonObject.getString("message"));
//                                    }
//                                }else {
//                                    return R.success().setMsg(jsonObject.getString("message"));
//                                }
//                            } else if (jsonObject.getInteger("code") == 200||jsonObject.getInteger("code") == 401) {
//                                log.error(jsonObject.toJSONString());
//                                return R.success().setMsg(jsonObject.getString("message"));
//                            } else {
//                                log.error(jsonObject.toJSONString());
//                                return R.error().setMsg("APP用户信息同步失败");
//                            }
//                        }
//                    } catch (Exception e) {
//                        throw new RRException("调用外部系统出错：" + APP_BASE_URL + "异常信息为：" + e.getMessage());
//                    }
                    return R.success().setMsg("用户注册成功,请等待审核");
//                } else {
//                    return R.error().setMsg("用户注册失败");
//                }

            } else {
                return R.error(1, "用户注册失败");
            }

        } catch (Exception e) {
            log.error("注册异常", e);
            return R.error(1, "注册异常");
        }
    }
}

