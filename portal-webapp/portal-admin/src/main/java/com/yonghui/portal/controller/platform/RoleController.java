package com.yonghui.portal.controller.platform;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.Role;
import com.yonghui.portal.service.global.RoleService;
import com.yonghui.portal.service.global.UserMenuService;
import com.yonghui.portal.util.ListToTreeUtils;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import com.yonghui.portal.utils.validator.ValidatorUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户角色表
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-15 20:03:17
 */
@RestController
@RequestMapping("/forfront/role")
public class RoleController extends AbstractController {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;
    @Autowired
    private UserMenuService userMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<Role> roleList = roleService.queryList(query);
        int total = roleService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(roleList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("forfront:role:select")
    public R select(@RequestParam Map<String, Object> map){

//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
        List<Role> list = roleService.queryList(map);

        return R.success().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("role:info")
    public R info(@PathVariable("id") Integer id) {
        Role role = roleService.queryObject(id);
        //查询角色对应的菜单
        List<Integer> menuIdList = roleService.queryMenuIdList(id);
        role.setMenuIdList(menuIdList);

        return R.success().put("role", role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("role:save")
    public R save(@RequestBody Role role) {
        ValidatorUtils.validateEntity(role);
        roleService.save(role);
        getRoleMenu(role);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("role:update")
    public R update(@RequestBody Role role) {
        ValidatorUtils.validateEntity(role);
        roleService.update(role);
        getRoleMenu(role);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("role:delete")
    public R delete(@RequestBody Integer[] ids) {
        roleService.deleteBatch(ids);
        for (Integer id:ids){
            redisBizUtilAdmin.removeRoleMenu(id);
        }
        return R.success();
    }

    public void getRoleMenu(Role role) {
        List<Menu> menus = new ArrayList<Menu>();
        List<Menu> menuList = new ArrayList<Menu>();
        menuList = userMenuService.listRoleMenu(role.getMenuIdList());
        menus = new ListToTreeUtils().listTreeMenu(menuList);
        JSONObject json = new JSONObject();
        json.put("data", menus);
        log.info("角色菜单数据:" + menuList);
        redisBizUtilAdmin.setRoleMenu(role.getRoleId(), json.toString());
    }

}
