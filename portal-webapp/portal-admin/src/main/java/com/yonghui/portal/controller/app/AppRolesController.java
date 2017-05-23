package com.yonghui.portal.controller.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppRolesService;
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
 * @date 2017-05-22 17:27:19
 */
@RestController
@RequestMapping("/app/roles")
public class AppRolesController extends AbstractController {
    @Autowired
    private AppRolesService appRolesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("approles:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<AppRoles> appRolesList = appRolesService.queryList(query);
        int total = appRolesService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appRolesList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("approles:info")
    public R info(@PathVariable("id") Integer id){
        AppRoles appRoles = appRolesService.queryObject(id);
        return R.success().put("appRoles", appRoles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("approles:save")
    public R save(@RequestBody AppRoles appRoles){
		appRolesService.save(appRoles);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("approles:update")
    public R update(@RequestBody AppRoles appRoles){
		appRolesService.update(appRoles);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("approles:delete")
    public R delete(@RequestBody Integer[] ids){
		appRolesService.deleteBatch(ids);
        return R.success();
    }

}
