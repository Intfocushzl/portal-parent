package com.yonghui.portal.controller.platform;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.init.InitProperties;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserAdminService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/16
 * Description :
 */
@RestController
@RequestMapping("/forfront/user")
public class UserController extends AbstractController {

    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private UserAdminService userAdminService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:list")
    public R list(HttpServletResponse response, @RequestParam Map<String, Object> params) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setContentType("text/html;charset=UTF-8");
        //查询列表数据
        Query query = new Query(params);

        List<User> userList = userAdminService.queryList(query);
        int total = userAdminService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:info")
    public R info(HttpServletResponse response, @PathVariable("id") Integer id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        User user = userAdminService.queryObject(id);
        return R.success().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:save")
    public R save(HttpServletResponse response, HttpServletRequest request, @RequestBody User user) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        userAdminService.save(user);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:update")
    public R update(HttpServletResponse response, @RequestBody User user) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        userAdminService.update(user);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:delete")
    public R delete(HttpServletResponse response, @RequestBody Integer[] ids) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        userAdminService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("/updateStatus")
    public R updateStatus(@RequestBody User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setStatus(user.getStatus());
        int res = userAdminService.updateStatus(user);

        User appUser = userAdminService.queryObjectAll(user.getId());
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> userMap = new HashedMap();
        userMap.put("user_name", appUser.getName());
        userMap.put("user_num", appUser.getJobNumber());
        userMap.put("user_pass", appUser.getPass());
        userMap.put("email", appUser.getEmail());
        userMap.put("mobile", appUser.getMobile());
        map.put("user", userMap);
        try {
            if (res == 1) {
                String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_USER_URL, JSON.json(map));

                System.out.println(result);
                if (!StringUtils.isEmpty(result)) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    if (jsonObject.getInteger("code") == 201) {
                        log.info(jsonObject.toJSONString());
                        return R.success().setMsg(jsonObject.getString("message"));
                    } else if (jsonObject.getInteger("code") == 200 || jsonObject.getInteger("code") == 401) {
                        log.error(jsonObject.toJSONString());
                        String info = jsonObject.getString("message");
                        return R.error().setMsg(info);
                    } else {
                        log.error(jsonObject.toJSONString());
                        return R.error().setMsg("APP新增用户同步失败");
                    }
                } else {
                    return R.error().setMsg("APP新增用户同步失败");
                }
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success();
    }

    @RequestMapping("/changeGrant/list")
    public R changeGrantList(HttpServletResponse response, @RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        System.out.println(query.toString());
        List<User> userList = userAdminService.queryChangeGrantList(query);
        int total = userAdminService.queryChangeGrantTotal(query);

        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    @RequestMapping("/changeGrant/pass")
    public R pass(@RequestBody User user) {
        int res = userAdminService.pass(user);
        if (res == 1) {

        } else {

        }
        return R.success();
    }

    @RequestMapping("/changeGrant/refuse")
    public R refuse(Integer id) {
        userAdminService.refuse(id + "");
        return R.success();
    }

    @RequestMapping("/newUserList")
    public R newUserList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        System.out.println(query.toString());
        List<User> userList = userAdminService.queryNewUserList(query);
        int total = userAdminService.queryNewUserTotal(query);

        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    @RequestMapping("/newUser/pass")
    public R userPass(@RequestBody User user) {
        user.setStatus(1);
        int res = userAdminService.updateStatus(user);
        if (res == 1) {
            return R.success();
        } else {
            return R.error().setMsg("审核异常");
        }
    }

    @RequestMapping("/newUser/refuse")
    public R userRefuse(Integer id) {
        User user = new User();
        user.setId(Long.valueOf(id));
        user.setStatus(-1);
        int res = userAdminService.updateStatus(user);
        if (res == 1) {
            return R.success();
        } else {
            return R.error().setMsg("审核异常");
        }
    }
}
