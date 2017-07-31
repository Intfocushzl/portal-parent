package com.yonghui.portal.controller.platform;

import com.alibaba.fastjson.JSON;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.global.MenuService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.utils.ShiroUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
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

    Logger log = Logger.getLogger(this.getClass());

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
    @RequiresPermissions("menu:select")
    public R select(@RequestParam Map<String, Object> params){
        //查询列表数据
        List<Menu> menuList = menuService.queryList(params);

        //添加顶级菜单
        Menu root = new Menu();
        root.setId(0L);
        root.setName("一级菜单");
        root.setPid(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.success().put("menuList", menuList);
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("menu:perms")
    public R perms(){
        //查询列表数据
        List<Menu> menuList = null;

        //只有超级管理员，才能查看所有管理员列表
//        if(getUserId() == Constant.SUPER_ADMIN){
            menuList = menuService.queryList(new HashMap<String, Object>());
//        }else{
//            menuList = menuService.queryUserList(getUserId());
//        }

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
        menu.setCreater(ShiroUtils.getUserId());
        menuService.save(menu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("menu:update")
    public R update(@RequestBody Menu menu) {
        menu.setModifier(ShiroUtils.getUserId());
        menuService.update(menu);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("menu:delete")
    public R delete(@RequestBody Integer[] ids) {

        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            Menu menu = menuService.queryObject(ids[i]);
            str.append("id:"+menu.getId()+"==title:"+menu.getName()+"===");
        }

        SysLog log = new SysLog();
        log.setIp(ComputerUtils.getIp());
        log.setUsername(ShiroUtils.getUserEntity().getUsername());
        log.setOperation(str.toString());

        menuService.savelog(log);
        menuService.deleteBatch(ids);
        return R.success();
    }


    @RequestMapping("/getLargeArea")
    public R getLargeArea(){
        //查询老大区列表数据
        List<Map<String,Object>> areaList = menuService.queryLargeAreaList();
        return R.success().put("largeArea", areaList);
    }
    @RequestMapping("/getAreaMans")
    public R getAreaMans(String largeArea){
        //查询新大区列表数据
        List<Map<String,Object>> areaMansList = menuService.queryAreamsList(largeArea);
        return R.success().put("areaMans", areaMansList);
    }
    @RequestMapping("/getFirms")
    public R getFirms(){
        //查询商行列表数据
        List<Map<String,Object>> firmList = menuService.queryFirmsList();
        return R.success().put("firm", firmList);
    }

    @RequestMapping("/getBravoShop")
    public R getBravoShopList(String largeArea,String areaMans){
        //查询门店列表数据
        Map<String,Object> map=new HashedMap();
        map.put("largeArea",largeArea);
        map.put("areaMans",areaMans);
        List<Map<String,Object>> shopList = menuService.queryShopsList(map);
        return R.success().put("shop", shopList);
    }

    @RequestMapping("/menuSortParentList")
    public R menuSortParentList(@RequestParam Map<String, Object> map) {
        int pid = 0;
        if (StringUtils.isNumeric(map.get("pid"))) {
            pid = Integer.parseInt(map.get("pid").toString());
        }

        List<Menu> menusList = null;
        try {
            menusList = menuService.queryChildrenList(pid);
            if (pid==0){
                menusList.remove(0);
            }
            System.out.println(JSON.toJSONString(menusList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.success().put("list", menusList);
    }

    @RequestMapping("/doMenuSort")
    public R doMenuSort(String sortStr) {
        System.out.println(sortStr);
        try {
            String[] sortStrs = sortStr.split("\\|");
            for (int i = 0; i < sortStrs.length; i++) {
                Long id=-1L;
                if (StringUtils.isNumeric(sortStrs[i].trim())){
                    id=Long.parseLong(sortStrs[i].trim());
                }
                Menu menu=new Menu();
                menu.setId(id);
                menu.setSort(i + 1);
                menuService.update(menu);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            return R.error();
        }
        return R.success();
    }

    @RequestMapping("/queryMenuSort")
    public R queryMenuSort(Integer id) {
        System.out.println(id);
        try {
            List<Menu> list=  menuService.queryMenuSort(id);
            if (list!=null&&list.size()>0){
                Menu menu=list.get(0);
                return R.success().put("sort",menu.getSort());
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            return R.error();
        }
        return R.success().put("sort",98);
    }

}
