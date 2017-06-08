package com.yonghui.portal.controller.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppUsers;
import com.yonghui.portal.service.app.AppUserRolesService;
import com.yonghui.portal.service.app.AppUsersService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 * 
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:18
 */
@RestController
@RequestMapping("/app/users")
public class AppUsersController extends AbstractController {
    @Autowired
    private AppUsersService appUsersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appusers:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<AppUsers> appUsersList = appUsersService.queryList(query);
        int total = appUsersService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appUsersList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appusers:info")
    public R info(@PathVariable("id") Integer id){
        AppUsers appUsers = appUsersService.queryObject(id);
        return R.success().put("appUsers", appUsers);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appusers:save")
    public R save(@RequestBody AppUsers appUsers){
        appUsers.setUserPass(Md5Util.getMd5("MD5", 0, null, "123456"));
		appUsersService.save(appUsers);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appusers:update")
    public R update(@RequestBody AppUsers appUsers){
		appUsersService.update(appUsers);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appusers:delete")
    public R delete(@RequestBody Integer[] ids){
		appUsersService.deleteBatch(ids);
        return R.success();
    }

}
