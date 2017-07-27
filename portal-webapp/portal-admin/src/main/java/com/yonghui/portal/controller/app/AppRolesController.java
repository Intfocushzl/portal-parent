package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.init.InitProperties;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.model.global.Role;
import com.yonghui.portal.service.global.RoleService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
@RestController
@RequestMapping("/app/roles")
public class AppRolesController extends AbstractController {

    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private RoleService roleService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("approles:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

//        List<AppRoles> appRolesList = appRolesService.queryList(query);
//        int total = appRolesService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(appRolesList, total, query.getLimit(), query.getPage());
//
//        return R.success().put("page", pageUtil);

        List<AppRoles> appRolesList = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("page", query.getPage() - 1);
        map.put("page_size", query.getLimit());
        if (query.get("roleName") != null) {
            map.put("role_name", query.get("roleName"));
        }
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_ROLE_URL, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    for (int i = 0; i < array.size(); i++) {
                        AppRoles appRoles = new AppRoles();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appRoles.setId(id);
                        Integer role_id = array.getJSONObject(i).getInteger("role_id");
                        appRoles.setRoleId(role_id);
                        String role_name = array.getJSONObject(i).getString("role_name");
                        appRoles.setRoleName(StringUtils.isEmpty(role_name) ? "" : role_name);
                        String memo = array.getJSONObject(i).getString("memo");
                        appRoles.setMemo(StringUtils.isEmpty(memo) ? "" : memo);
                        String created_at = array.getJSONObject(i).getString("created_at");
                        appRoles.setCreatedAt(StringUtils.isEmpty(created_at) ? "" : created_at);
                        appRolesList.add(appRoles);
                    }
                    int total_count = jsonObject.getInteger("total_count");
                    PageUtils pageUtil = new PageUtils(appRolesList, total_count, query.getLimit(), query.getPage());

                    return R.success().put("page", pageUtil);
                } else if (jsonObject.getInteger("code") == 401) {
                    appRolesList = new ArrayList<>();
                    PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
                    String info = jsonObject.getString("message");
                    return R.error().put("page", pageUtil).setMsg(info);
                } else {
                    return R.error().setMsg("APP查询失败");
                }
            } else {
                appRolesList = new ArrayList<>();
                PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
                return R.error().put("page", pageUtil);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appRolesList = new ArrayList<>();
            PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
            return R.error().put("page", pageUtil);
        }
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> map) {
        List<AppRoles> list = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("page_size", 1000);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_ROLE_URL, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    int total = array.size();
                    for (int i = 0; i < total; i++) {
                        AppRoles appRoles = new AppRoles();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appRoles.setId(id);
                        Integer role_id = array.getJSONObject(i).getInteger("role_id");
                        appRoles.setRoleId(role_id);
                        String role_name = array.getJSONObject(i).getString("role_name");
                        appRoles.setRoleName(role_name);
                        String memo = array.getJSONObject(i).getString("memo");
                        appRoles.setMemo(memo);
                        list.add(appRoles);
                    }

                    return R.success().put("list", list);
                } else if (jsonObject.getInteger("code") == 401) {
                    String info = jsonObject.getString("message");
                    return R.error().put("list", list).setMsg(info);
                } else {
                    return R.error().setMsg("APP查询失败");
                }
            } else {
                return R.error().put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().put("list", list);
        }
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
//        List<AppRoles> list = appRolesService.queryList(map);
//
//        return R.success().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("approles:info")
    public R info(@PathVariable("id") Integer id) {
//        AppRoles appRoles = appRolesService.queryObject(id);
//
//        //查询角色对应的菜单
//        List<Map<String,Object>> menuList = appRolesService.queryMenuList(id);
//        appRoles.setMenuList(menuList);
//
//        return R.success().put("appRoles", appRoles);
        Map<String, Object> map = new HashedMap();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("role_id", id);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + id, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONObject obj = jsonObject.getJSONObject("data");
                    AppRoles appRoles = new AppRoles();
                    Integer xid = obj.getInteger("id");
                    appRoles.setId(xid);
                    Integer role_id = obj.getInteger("role_id");
                    appRoles.setRoleId(role_id);
                    String role_name = obj.getString("role_name");
                    appRoles.setRoleName(role_name);
                    String memo = obj.getString("memo");
                    appRoles.setMemo(memo);

                    return R.success().put("appRoles", appRoles);
                } else {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                }
            } else {
                return R.error().setMsg("APP查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().setMsg("APP查询失败");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("approles:save")
    public R save(@RequestBody AppRoles appRoles) {
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("role_id", appRoles.getRoleId());
        appMap.put("role_name", appRoles.getRoleName());
        appMap.put("memo", appRoles.getMemo());
        map.put("role", appMap);
        List<Map<String, Object>> menus = appRoles.getMenuList();
        Role role=new Role();
        role.setRoleId( appRoles.getRoleId());
        role.setName(appRoles.getRoleName());
        role.setRemark(appRoles.getMemo());
        role.setStatus(1);
        role.setType(1);
        role.setMenuIdList(new ArrayList<>());
        roleService.save(role);
        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL, JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                   return updateMenu(httpUtil, appRoles.getRoleId(), menus);
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
//        appRolesService.save(appRoles);
//        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("approles:update")
    public R update(@RequestBody AppRoles appRoles) {
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("role_id", appRoles.getRoleId());
        appMap.put("role_name", appRoles.getRoleName());
        appMap.put("memo", appRoles.getMemo());
        map.put("role", appMap);
        Role role=new Role();
        role.setRoleId( appRoles.getRoleId());
        role.setName(appRoles.getRoleName());
        role.setRemark(appRoles.getMemo());
        role.setType(1);
        roleService.update(role);
        List<Map<String, Object>> menus = appRoles.getMenuList();

        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + appRoles.getRoleId(), JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    updateMenu(httpUtil, appRoles.getRoleId(), menus);
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
//        appRolesService.update(appRoles);
//        return R.success();
    }

    /**
     * 为角色配置报表菜单权限
     */
    public R updateMenu(HttpMethodUtil httpUtil, Integer roleId, List<Map<String, Object>> menus) {
        log.info("============>更新角色-菜单开始");
        List<Integer> kpi_ids = new ArrayList<>();
        List<Integer> analyse_ids = new ArrayList<>();
        List<Integer> app_ids = new ArrayList<>();

        for (Map<String, Object> menu : menus) {
            Integer type = Integer.parseInt(menu.get("type") == null ? "0" : menu.get("type") + "");
            String menuId = menu.get("menuId") + "";
            if (type == 1 && StringUtils.isNumeric(menuId)) {
                kpi_ids.add(Integer.parseInt(menuId));
            }
            if (type == 2 && StringUtils.isNumeric(menuId)) {
                analyse_ids.add(Integer.parseInt(menuId));
            }
            if (type == 3 && StringUtils.isNumeric(menuId)) {
                app_ids.add(Integer.parseInt(menuId));
            }
        }
        System.err.println("1-->menuId====>"+JSON.toJSONString(kpi_ids));
        System.err.println("2-->menuId====>"+JSON.toJSONString(analyse_ids));
        System.err.println("3-->menuId====>"+JSON.toJSONString(app_ids));
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("kpi_ids", kpi_ids);
        try {
            String info = "";
            int x = 0;
            String result1 = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/kpis", JSON.toJSONString(map));
            if (!StringUtils.isEmpty(result1)) {
                JSONObject jsonObject = JSONObject.parseObject(result1);
                if (jsonObject.getInteger("code") == 201) {
                    log.info(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/kpis"+"\n"+result1);
                    map.clear();
                    map.put("api_token", "api_token");
                    map.put("analyse_ids", analyse_ids);
                    String result2 = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/analyses", JSON.toJSONString(map));
                    if (!StringUtils.isEmpty(result2)) {
                         jsonObject = JSONObject.parseObject(result2);
                        if (jsonObject.getInteger("code") == 201) {
                            log.info(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/analyses"+"\n"+result2);
                            map.clear();
                            map.put("api_token", "api_token");
                            map.put("app_ids", app_ids);
                            String result3 = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/apps", JSON.toJSONString(map));
                            if (!StringUtils.isEmpty(result3)) {
                                 jsonObject = JSONObject.parseObject(result3);
                                if (jsonObject.getInteger("code") == 201) {
                                    info = jsonObject.getString("message");
                                    log.info(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/apps"+"\n"+result3);
                                    log.info("============>更新角色-菜单结束");
                                    return R.success().setMsg(info);
                                } else {
                                    info = jsonObject.getString("message");
                                    return R.error().setMsg(info);
                                }
                            } else {
                                return R.error();
                            }
                        } else {
                            info = jsonObject.getString("message");
                            return R.error().setMsg(info);
                        }
                    } else {
                        return R.error();
                    }
                } else {
                    info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                }
            } else {
                return R.error();
            }

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("approles:delete")
    public R delete(@RequestBody Integer[] ids) {
//        appRolesService.deleteBatch(ids);
//        return R.success();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("id", ids[0]);
        try {
            String result = httpUtil.getDeleteResult(InitProperties.APP_BASE_POST_ROLE_URL, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    return R.success();
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP角色删除失败");
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
     * 角色对应菜单
     */
    @RequestMapping("/selectKpisMenu")
    public R selectKpisMenu(@RequestParam Integer roleId, @RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("lazy_load", true);

        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/kpis", map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                List<AppMenu> menuList = new ArrayList<>();

                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    int total = array.size();
                    for (int i = 0; i < total; i++) {
                        AppMenu appMenu = new AppMenu();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appMenu.setId(id);
                        appMenu.setMenuId(id);
                        String group_name = array.getJSONObject(i).getString("kpi_group");
                        appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
                        String name = array.getJSONObject(i).getString("kpi_name");
                        appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);

                        String url = array.getJSONObject(i).getString("url");
                        appMenu.setUrl(StringUtils.isEmpty(url) ? "" : url);
                        String icon = array.getJSONObject(i).getString("icon");
                        appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
                        String icon_link = array.getJSONObject(i).getString("icon_link");
                        appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);

                        String remark = array.getJSONObject(i).getString("remark");
                        appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);

                        Boolean publicly = array.getJSONObject(i).getBoolean("publicly");
                        if (publicly != null) {
                            appMenu.setPublicly(publicly);
                        }

                        Integer healthValue = array.getJSONObject(i).getInteger("health_value");
                        if (healthValue != null) {
                            appMenu.setHealthValue(healthValue);
                        }
                        Integer groupOrder = array.getJSONObject(i).getInteger("group_order");
                        if (groupOrder != null) {
                            appMenu.setGroupOrder(groupOrder);
                        }
                        Integer itemOrder = array.getJSONObject(i).getInteger("item_order");
                        if (itemOrder != null) {
                            appMenu.setItemOrder(itemOrder);
                        }

                        appMenu.setType(1);

                        String createdAt = array.getJSONObject(i).getString("created_at");
                        appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
                        String updatedAt = array.getJSONObject(i).getString("updated_at");
                        appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
                        Boolean active = array.getJSONObject(i).getBoolean("active");
                        appMenu.setActive((active != null && active) ? 1 : 0);
                        menuList.add(appMenu);
                    }
//                    for (AppMenu appMenu : menuList) {
//                        Map<String, Object> node = new HashedMap();
//                        node.put("id", appMenu.getMenuId());
//                        node.put("name", appMenu.getSubName2());
//
//                        List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
//                        Map<String, Object> childrenNode = new HashedMap();
//                        childrenNode.put("id", appMenu.getMenuId());
//                        childrenNode.put("name", appMenu.getTitle());
//                        childrenNode.put("type", appMenu.getType());
//
//                        boolean isExist = false;
//                        for (int i = 0; i < list.size(); i++) {
//                            String name = list.get(i).get("name") + "";
//                            if (name.equals(appMenu.getSubName2())) {
//                                isExist = true;
//                                children = (List<Map<String, Object>>) list.get(i).get("children");
//                                children.add(childrenNode);
//                                break;
//                            }
//                        }
//                        if (!isExist) {
//                            children.add(childrenNode);
//                            node.put("children", children);
//                            list.add(node);
//                        }
//                    }
                    List<AppMenu> menuKpiList = new ArrayList<>();

                    for (AppMenu appMenu : menuList) {
                        if (appMenu.getActive() == 1) {
                            menuKpiList.add(appMenu);
                        }
                    }
                    list = parseListToTree(menuList);
                    return R.success().put("list", list).put("menuKpiList", menuKpiList);
                } else {
                    String info = jsonObject.getString("message");
                    return R.error().put("list", list).setMsg(info);
                }
            } else {
                return R.error().put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().put("list", list);
        }
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
//        List<AppRoles> list = appRolesService.queryList(map);
//
//        return R.success().put("list", list);
    }

    @RequestMapping("/selectAnalysesMenu")
    public R selectAnalysesMenu(@RequestParam Integer roleId, @RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("lazy_load", true);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/analyses", map);

            if (!StringUtils.isEmpty(result)) {
                List<AppMenu> menuList = new ArrayList<>();

                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    int total = array.size();
                    for (int i = 0; i < total; i++) {
                        AppMenu appMenu = new AppMenu();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appMenu.setId(id);
                        appMenu.setMenuId(id);
                        String category = array.getJSONObject(i).getString("category");
                        appMenu.setSubName1(StringUtils.isEmpty(category) ? "" : category);
                        String group_name = array.getJSONObject(i).getString("group_name");
                        appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
                        String name = array.getJSONObject(i).getString("name");
                        appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);

                        String link_path = array.getJSONObject(i).getString("link_path");
                        appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
                        String icon = array.getJSONObject(i).getString("icon");
                        appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
                        String icon_link = array.getJSONObject(i).getString("icon_link");
                        appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);

                        String remark = array.getJSONObject(i).getString("remark");
                        appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);

                        Boolean publicly = array.getJSONObject(i).getBoolean("publicly");
                        if (publicly != null) {
                            appMenu.setPublicly(publicly);
                        }

                        Integer healthValue = array.getJSONObject(i).getInteger("health_value");
                        if (healthValue != null) {
                            appMenu.setHealthValue(healthValue);
                        }
                        Integer groupOrder = array.getJSONObject(i).getInteger("group_order");
                        if (groupOrder != null) {
                            appMenu.setGroupOrder(groupOrder);
                        }
                        Integer itemOrder = array.getJSONObject(i).getInteger("item_order");
                        if (itemOrder != null) {
                            appMenu.setItemOrder(itemOrder);
                        }

                        appMenu.setType(2);

                        String createdAt = array.getJSONObject(i).getString("created_at");
                        appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
                        String updatedAt = array.getJSONObject(i).getString("updated_at");
                        appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
                        Boolean active = array.getJSONObject(i).getBoolean("active");
                        appMenu.setActive((active != null && active) ? 1 : 0);
                        menuList.add(appMenu);
                    }
                    for (int k = 0; k < menuList.size(); k++) {
                        AppMenu appMenu = menuList.get(k);
                        Map<String, Object> aNode = new HashedMap();
                        aNode.put("id", "a" + appMenu.getId());
                        aNode.put("name", appMenu.getSubName1());
                        aNode.put("pname", appMenu.getName() + "报表");
                        List<AppMenu> aChild = new ArrayList<>();

                        boolean bIsExist = false;
                        for (int i = 0; i < list.size(); i++) {
                            String aName = list.get(i).get("name") + "";
                            String bPName = appMenu.getSubName1();
                            if (bPName.equals(aName)) {
                                bIsExist = true;
                                aChild = (List<AppMenu>) list.get(i).get("children");
                                aChild.add(appMenu);
                            }
                        }

                        if (!bIsExist) {
                            aChild.add(appMenu);
                            aNode.put("children", aChild);
                            list.add(aNode);
                        }
                    }
                    List<Map<String, Object>> newList = new ArrayList<>();
                    for (int k = 0; k < list.size(); k++) {
                        Map<String, Object> newMap = new HashedMap();
                        String id = list.get(k).get("id") + "";
                        String name = list.get(k).get("name") + "";

                        List<AppMenu> appMenus = (List<AppMenu>) list.get(k).get("children");
                        List<Map<String, Object>> children = parseListToTree(appMenus);

                        newMap.put("id", id);
                        newMap.put("name", name);
                        newMap.put("children", children);
                        newList.add(newMap);
                    }

                    List<AppMenu> menuAnalysesList = new ArrayList<>();

                    for (AppMenu appMenu : menuList) {
                        if (appMenu.getActive() == 1) {
                            menuAnalysesList.add(appMenu);
                        }
                    }
                    return R.success().put("list", newList).put("menuAnalysesList", menuAnalysesList);
                } else {
                    String info = jsonObject.getString("message");
                    return R.error().put("list", list).setMsg(info);
                }
            } else {
                return R.error().put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().put("list", list);
        }
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
//        List<AppRoles> list = appRolesService.queryList(map);
//
//        return R.success().put("list", list);
    }

    public List<Map<String, Object>> parseListToTree(List<AppMenu> menuList) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppMenu appMenu : menuList) {
            Map<String, Object> node = new HashedMap();
            node.put("id", "b" + appMenu.getMenuId());
            node.put("name", appMenu.getSubName2());

            List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
            Map<String, Object> childrenNode = new HashedMap();
            childrenNode.put("id", appMenu.getMenuId());
            childrenNode.put("name", appMenu.getTitle());
            childrenNode.put("type", appMenu.getType());

            boolean isExist = false;
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).get("name") + "";
                if (name.equals(appMenu.getSubName2())) {
                    isExist = true;
                    children = (List<Map<String, Object>>) list.get(i).get("children");
                    children.add(childrenNode);
                    break;
                }
            }
            if (!isExist) {
                children.add(childrenNode);
                node.put("children", children);
                list.add(node);
            }
        }
        return list;
    }

    @RequestMapping("/selectAppsMenu")
    public R selectAppsMenu(@RequestParam Integer roleId, @RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("lazy_load", true);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ROLE_URL + "/" + roleId + "/apps", map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                List<AppMenu> menuList = new ArrayList<>();

                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    int total = array.size();
                    for (int i = 0; i < total; i++) {
                        AppMenu appMenu = new AppMenu();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appMenu.setId(id);
                        appMenu.setMenuId(id);
                        String group_name = array.getJSONObject(i).getString("group_name");
                        appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
                        String name = array.getJSONObject(i).getString("name");
                        appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);
                        String link_path = array.getJSONObject(i).getString("link_path");
                        appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
                        String icon = array.getJSONObject(i).getString("icon");
                        appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
                        String icon_link = array.getJSONObject(i).getString("icon_link");
                        appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);

                        String remark = array.getJSONObject(i).getString("remark");
                        appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);

                        Boolean publicly = array.getJSONObject(i).getBoolean("publicly");
                        if (publicly != null) {
                            appMenu.setPublicly(publicly);
                        }

                        Integer healthValue = array.getJSONObject(i).getInteger("health_value");
                        if (healthValue != null) {
                            appMenu.setHealthValue(healthValue);
                        }
                        Integer groupOrder = array.getJSONObject(i).getInteger("group_order");
                        if (groupOrder != null) {
                            appMenu.setGroupOrder(groupOrder);
                        }
                        Integer itemOrder = array.getJSONObject(i).getInteger("item_order");
                        if (itemOrder != null) {
                            appMenu.setItemOrder(itemOrder);
                        }

                        appMenu.setType(3);

                        String createdAt = array.getJSONObject(i).getString("created_at");
                        appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
                        String updatedAt = array.getJSONObject(i).getString("updated_at");
                        appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
                        Boolean active = array.getJSONObject(i).getBoolean("active");
                        appMenu.setActive((active != null && active) ? 1 : 0);
                        menuList.add(appMenu);
                    }
//                    for (AppMenu appMenu : menuList) {
//                        Map<String, Object> node = new HashedMap();
//                        node.put("id", appMenu.getMenuId());
//                        node.put("name", appMenu.getSubName2());
//
//                        List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
//                        Map<String, Object> childrenNode = new HashedMap();
//                        childrenNode.put("id", appMenu.getMenuId());
//                        childrenNode.put("name", appMenu.getTitle());
//                        childrenNode.put("type", appMenu.getType());
//
//                        boolean isExist = false;
//                        for (int i = 0; i < list.size(); i++) {
//                            String name = list.get(i).get("name") + "";
//                            if (name.equals(appMenu.getSubName2())) {
//                                isExist = true;
//                                children = (List<Map<String, Object>>) list.get(i).get("children");
//                                children.add(childrenNode);
//                                break;
//                            }
//                        }
//                        if (!isExist) {
//                            children.add(childrenNode);
//                            node.put("children", children);
//                            list.add(node);
//                        }
//                    }
                    list = parseListToTree(menuList);
                    List<AppMenu> menuAppList = new ArrayList<>();

                    for (AppMenu appMenu : menuList) {
                        if (appMenu.getActive() == 1) {
                            menuAppList.add(appMenu);
                        }
                    }
                    return R.success().put("list", list).put("menuAppList", menuAppList);
                } else {
                    String info = jsonObject.getString("message");
                    return R.error().put("list", list).setMsg(info);
                }
            } else {
                return R.error().put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().put("list", list);
        }
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
//        List<AppRoles> list = appRolesService.queryList(map);
//
//        return R.success().put("list", list);
    }
}
