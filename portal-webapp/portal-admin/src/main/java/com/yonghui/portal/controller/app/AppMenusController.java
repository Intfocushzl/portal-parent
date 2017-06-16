package com.yonghui.portal.controller.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.service.app.AppMenusService;
import org.apache.commons.collections.map.HashedMap;
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
 * @date 2017-05-22 17:27:20
 */
@RestController
@RequestMapping("app/menus")
public class AppMenusController extends AbstractController {
    @Autowired
    private AppMenusService appMenusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<AppMenu> appMenusList = appMenusService.queryList(query);
        int total = appMenusService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appMenusList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info")
    @RequiresPermissions("appmenu:info")
    public R info(Integer menuId,Integer type){
        Map<String, Object> params=new HashedMap();
        params.put("menuId",menuId);
        params.put("type",type);
        AppMenu appMenu = appMenusService.queryObject(params);
        return R.success().put("appMenu", appMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appmenu:save")
    public R save(@RequestBody AppMenu appMenu){
        appMenusService.save(appMenu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appmenu:update")
    public R update(@RequestBody AppMenu appMenu){
        appMenusService.update(appMenu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appmenu:delete")
    public R delete(@RequestBody Map<String, Object> params){
        appMenusService.deleteBatch(params);
        return R.success();
    }

}
