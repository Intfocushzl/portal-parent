package com.yonghui.portal.controller.platform;

import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserAdminService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @ResponseBody
    @IgnoreAuth
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
    @ResponseBody
    @IgnoreAuth
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
    @ResponseBody
    @IgnoreAuth
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
    @ResponseBody
    @IgnoreAuth
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
    @ResponseBody
    @IgnoreAuth
    @RequestMapping("/delete")
    @RequiresPermissions("user:delete")
    public R delete(HttpServletResponse response, @RequestBody Integer[] ids) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        userAdminService.deleteBatch(ids);
        return R.success();
    }

}
