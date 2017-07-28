package com.yonghui.portal.controller.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.init.InitProperties;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.Role;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.global.RoleService;
import com.yonghui.portal.service.global.UserMenuService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import com.yonghui.portal.utils.ShiroUtils;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import com.yonghui.portal.utils.validator.ValidatorUtils;
import org.apache.commons.collections.map.HashedMap;
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
    public R select(@RequestParam Map<String, Object> map) {

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
        role.setCreater(getUserId());
        ValidatorUtils.validateEntity(role);
        role.setCreater(ShiroUtils.getUserId());
        roleService.save(role);
        getRoleMenu(role);
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("role_id", role.getRoleId());
        appMap.put("role_name", role.getName());
        appMap.put("memo", role.getRemark());
        map.put("role", appMap);

        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL, JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    return R.success();
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP角色添加失败");
                }
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("role:update")
    public R update(@RequestBody Role role) {
        ValidatorUtils.validateEntity(role);
        role.setModifier(ShiroUtils.getUserId());
        roleService.update(role);
        getRoleMenu(role);
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("role_id", role.getRoleId());
        appMap.put("role_name", role.getName());
        appMap.put("memo", role.getRemark());
        map.put("role", appMap);

        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + role.getRoleId(), JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    return R.success();
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP角色更新失败");
                }
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("role:delete")
    public R delete(@RequestBody Integer[] ids) {

        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            Role role = roleService.queryObject(ids[i]);
            str.append("roleid:"+role.getId()+"==name:"+role.getName()+"===");
        }

        SysLog log = new SysLog();
        log.setIp(ComputerUtils.getIp());
        log.setUsername(ShiroUtils.getUserEntity().getUsername());
        log.setOperation(str.toString());

        roleService.savelog(log);

        roleService.deleteBatch(ids);
        for (Integer id : ids) {
            redisBizUtilAdmin.removeRoleMenu(id);
        }
        return R.success();
    }

    public void getRoleMenu(Role role) {
        List<Menu> menus = new ArrayList<Menu>();
        List<Menu> menuList = userMenuService.listRoleMenu(role.getMenuIdList());
        menus = new ListToTreeUtils().listTreeMenu(menuList);
        JSONObject json = new JSONObject();
        json.put("data", menus);
        if (menuList == null) {
            menuList = new ArrayList<Menu>();
        }
        log.info("角色菜单数据-:" + json.toString());
        redisBizUtilAdmin.setRoleMenu(role.getRoleId(), json.toString());
    }

    //一键缓存
    @RequestMapping("/addRedis")
    public R addRedis(@RequestBody Integer[] ids) {
        for (Integer id : ids) {
            List<Menu> menuList = roleService.queryMenuList(id);
            List<Menu> menus = new ListToTreeUtils().listTreeMenu(menuList);
            JSONObject json = new JSONObject();
            json.put("data", menus);
            if (menuList == null) {
                menuList = new ArrayList<Menu>();
            }
            log.info("角色菜单数据-:" + json.toString());
            redisBizUtilAdmin.setRoleMenu(id, json.toString());
        }
        return R.success();
    }

    @RequestMapping("/getNextRoleId")
    public R getNextRoleId() {
        int roleId = roleService.getNextRoleId();
        Map<String, Object> result = new HashedMap();
        result.put("nextRoleId", roleId);
        return R.success().setData(result);
    }

}
