package com.yonghui.portal.controller.app;

import java.util.ArrayList;
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

    @RequestMapping("/select")
    //@RequiresPermissions("app:menu:select")
    public R select(){
        //查询列表数据
        List<AppMenu> appMenusList = appMenusService.queryAllMenuList();

        List<Map<String,Object>> list=new ArrayList<>();

        Map<String,Object> map1=new HashedMap();
        map1.put("id",-1);
        map1.put("name","生意概况");
        map1.put("type",1);
        Map<String,Object> map2=new HashedMap();
        map2.put("id",-2);
        map2.put("name","报表");
        map2.put("type",2);
        Map<String,Object> map3=new HashedMap();
        map3.put("id",-3);
        map3.put("name","专题");
        map3.put("type",3);

        List<Map<String,Object>> list1=new ArrayList<>();
        List<Map<String,Object>> list2=new ArrayList<>();
        List<Map<String,Object>> list3=new ArrayList<>();

        for (AppMenu appMenu:appMenusList) {
            Map<String,Object> node=new HashedMap();
            node.put("id",(-1)*appMenu.getMenuId());
            node.put("name",appMenu.getName());
            node.put("type",appMenu.getType());
            node.put("second",appMenu.getMenuId());

            List<Map<String,Object>> children=new  ArrayList<Map<String,Object>>();
            Map<String,Object> childrenNode=new HashedMap();
            childrenNode.put("id",appMenu.getMenuId());
            childrenNode.put("name",appMenu.getSubName());
            childrenNode.put("type",appMenu.getType());

            if (appMenu.getType()==1){
                boolean isExist =false;
                for (int i=0;i<list1.size();i++){
                    String name=list1.get(i).get("name")+"";
                    if (name.equals(appMenu.getName())){
                        isExist = true;
                        children= (List<Map<String, Object>>) list1.get(i).get("children");
                        children.add(childrenNode);
                        break ;
                    }
                }
                if (!isExist){
                    children.add(childrenNode);
                    node.put("children",children);
                    list1.add(node);
                }
            }
            if (appMenu.getType()==2){
                boolean isExist =false;
                for (int i=0;i<list2.size();i++){
                    String name=list2.get(i).get("name")+"";
                    if (name.equals(appMenu.getName())){
                        isExist = true;
                        children= (List<Map<String, Object>>) list2.get(i).get("children");
                        children.add(childrenNode);
                        break ;
                    }
                }
                if (!isExist){
                    children.add(childrenNode);
                    node.put("children",children);
                    list2.add(node);
                }
            }
            if (appMenu.getType()==3){
                boolean isExist =false;
                for (int i=0;i<list3.size();i++){
                    String name=list3.get(i).get("name")+"";
                    if (name.equals(appMenu.getName())){
                        isExist = true;
                        children= (List<Map<String, Object>>) list3.get(i).get("children");
                        children.add(childrenNode);
                        break ;
                    }
                }
                if (!isExist){
                    children.add(childrenNode);
                    node.put("children",children);
                    list3.add(node);
                }
            }
        }

        map1.put("children",list1);
        map2.put("children",list2);
        map3.put("children",list3);

        list.add(map1);
        list.add(map2);
        list.add(map3);

        return R.success().put("appMenuList", list);
    }
}
