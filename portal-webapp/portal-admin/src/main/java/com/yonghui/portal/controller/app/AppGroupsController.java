package com.yonghui.portal.controller.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.model.app.AppGroups;
import com.yonghui.portal.service.app.AppGroupsService;
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
@RequestMapping("/app/groups")
public class AppGroupsController extends AbstractController {
    @Autowired
    private AppGroupsService appGroupsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appgroups:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<AppGroups> appGroupsList = appGroupsService.queryList(query);
        int total = appGroupsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appGroupsList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appgroups:info")
    public R info(@PathVariable("id") Integer id){
        AppGroups appGroups = appGroupsService.queryObject(id);
        return R.success().put("appGroups", appGroups);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appgroups:save")
    public R save(@RequestBody AppGroups appGroups){
		appGroupsService.save(appGroups);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appgroups:update")
    public R update(@RequestBody AppGroups appGroups){
		appGroupsService.update(appGroups);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appgroups:delete")
    public R delete(@RequestBody Integer[] ids){
		appGroupsService.deleteBatch(ids);
        return R.success();
    }

}
