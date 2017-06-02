package com.yonghui.portal.controller.platform;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.service.global.MenuService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/18
 * Description :
 */
@RestController
@RequestMapping("/forfront/menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuService menuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("menu:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<Menu> menuList = menuService.queryList(query);
        int total = menuService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(menuList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(@RequestParam Map<String, Object> params){
        //查询列表数据
        List<Menu> menuList = menuService.queryList(params);

        //添加顶级菜单
        Menu root = new Menu();
        root.setId(0);
        root.setName("一级菜单");
        root.setPid(-1);
        root.setOpen(true);
        menuList.add(root);

        return R.success().put("menuList", menuList);
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    public R perms(){
        //查询列表数据
        List<Menu> menuList = null;

        //只有超级管理员，才能查看所有管理员列表
        if(getUserId() == Constant.SUPER_ADMIN){
            menuList = menuService.queryList(new HashMap<String, Object>());
        }else{
            menuList = menuService.queryUserList(getUserId());
        }

        return R.success().put("menuList", menuList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("menu:info")
    public R info(@PathVariable("id") Integer id) {
        Menu menu = menuService.queryObject(id);
        return R.success().put("menu", menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("menu:save")
    public R save(@RequestBody Menu menu) {
        menuService.save(menu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("menu:update")
    public R update(@RequestBody Menu menu) {
        menuService.update(menu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("menu:delete")
    public R delete(@RequestBody Integer[] ids) {
        menuService.deleteBatch(ids);
        return R.success();
    }

}
