package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppRolesService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
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

    @Autowired
    private AppRolesService appRolesService;

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
        try {
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_ROLE_URL, map);

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
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_ROLE_URL, map);

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
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL + "/" + id, map);

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
        appMap.put("role_name", appRoles.getRoleName());
        appMap.put("memo", appRoles.getMemo());
        map.put("role", appMap);
        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL, JSON.toJSONString(map));

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
        appMap.put("role_name", appRoles.getRoleName());
        appMap.put("memo", appRoles.getMemo());
        map.put("role", appMap);
        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL + "/" + appRoles.getId(), JSON.toJSONString(map));

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
//        appRolesService.update(appRoles);
//        return R.success();
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
            String result = httpUtil.getDeleteResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL, map);

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
     * 角色列表
     */
    @RequestMapping("/selectMenu")
    public R selectMenu(@RequestParam Integer roleId, @RequestParam Map<String, Object> map) {
        List<AppMenu> list = new ArrayList<>();

//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        map.put("api_token", "api_token");
//        try {
//            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL + "/" + roleId + "/analyses", map);
//
//            System.out.println(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 200) {
//
//                    JSONArray array = jsonObject.getJSONArray("data");
//                    if (array == null) {
//                        array = new JSONArray();
//                    }
//                    int total = array.size();
//                    for (int i = 0; i < total; i++) {
//                        AppMenu appMenu = new AppMenu();
//                        Integer id = array.getJSONObject(i).getInteger("id");
//                        appMenu.setId(id);
//                        appMenu.setMenuId(id);
//                        String category = array.getJSONObject(i).getString("category");
//                        appMenu.setSubName1(StringUtils.isEmpty(category) ? "" : category);
//                        String group_name = array.getJSONObject(i).getString("group_name");
//                        appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
//                        String name = array.getJSONObject(i).getString("name");
//                        appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);
//
//                        String link_path = array.getJSONObject(i).getString("link_path");
//                        appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
//                        String icon = array.getJSONObject(i).getString("icon");
//                        appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
//                        String icon_link = array.getJSONObject(i).getString("icon_link");
//                        appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);
//
//                        String remark = array.getJSONObject(i).getString("remark");
//                        appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);
//
//                        Boolean publicly = array.getJSONObject(i).getBoolean("publicly");
//                        if (publicly != null) {
//                            appMenu.setPublicly(publicly);
//                        }
//
//                        Integer healthValue = array.getJSONObject(i).getInteger("health_value");
//                        if (healthValue != null) {
//                            appMenu.setHealthValue(healthValue);
//                        }
//                        Integer groupOrder = array.getJSONObject(i).getInteger("group_order");
//                        if (groupOrder != null) {
//                            appMenu.setGroupOrder(groupOrder);
//                        }
//                        Integer itemOrder = array.getJSONObject(i).getInteger("item_order");
//                        if (itemOrder != null) {
//                            appMenu.setItemOrder(itemOrder);
//                        }
//
//                        appMenu.setType(2);
//
//                        String createdAt = array.getJSONObject(i).getString("created_at");
//                        appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
//                        String updatedAt = array.getJSONObject(i).getString("updated_at");
//                        appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
//                        list.add(appMenu);
//                    }
//
//                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ROLE_URL + "/" + roleId + "/apps", map);
//
//                    System.out.println(result);
//                    if (!StringUtils.isEmpty(result)) {
//                        jsonObject = JSONObject.parseObject(result);
//                        if (jsonObject.getInteger("code") == 200) {
//
//                            array = jsonObject.getJSONArray("data");
//                            if (array == null) {
//                                array = new JSONArray();
//                            }
//                            total = array.size();
//                            for (int i = 0; i < total; i++) {
//                                AppMenu appMenu = new AppMenu();
//                                Integer id = array.getJSONObject(i).getInteger("id");
//                                appMenu.setId(id);
//                                appMenu.setMenuId(id);
//                                String group_name3 = array.getJSONObject(i).getString("group_name");
//                                appMenu.setSubName1(StringUtils.isEmpty(group_name3) ? "" : group_name3);
//                                String name3 = array.getJSONObject(i).getString("name");
//                                appMenu.setSubName2(StringUtils.isEmpty(name3) ? "" : name3);
//                                String link_path = array.getJSONObject(i).getString("link_path");
//                                appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
//                                String icon = array.getJSONObject(i).getString("icon");
//                                appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
//                                String icon_link = array.getJSONObject(i).getString("icon_link");
//                                appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);
//
//                                String remark = array.getJSONObject(i).getString("remark");
//                                appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);
//
//                                Boolean publicly = array.getJSONObject(i).getBoolean("publicly");
//                                if (publicly != null) {
//                                    appMenu.setPublicly(publicly);
//                                }
//
//                                Integer healthValue = array.getJSONObject(i).getInteger("health_value");
//                                if (healthValue != null) {
//                                    appMenu.setHealthValue(healthValue);
//                                }
//                                Integer groupOrder = array.getJSONObject(i).getInteger("group_order");
//                                if (groupOrder != null) {
//                                    appMenu.setGroupOrder(groupOrder);
//                                }
//                                Integer itemOrder = array.getJSONObject(i).getInteger("item_order");
//                                if (itemOrder != null) {
//                                    appMenu.setItemOrder(itemOrder);
//                                }
//
//                                appMenu.setType(3);
//
//                                String createdAt = array.getJSONObject(i).getString("created_at");
//                                appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
//                                String updatedAt = array.getJSONObject(i).getString("updated_at");
//                                appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
//                                list.add(appMenu);
//
//                            }
//                            return R.success().put("list", list);
//                        } else {
//                            String info = jsonObject.getString("message");
//                            return R.error().put("list", list).setMsg(info);
//                        }
//                    } else {
//                        return R.error().put("list", list);
//                    }
//
//                } else {
//                    String info = jsonObject.getString("message");
//                    return R.error().put("list", list).setMsg(info);
//                }
//            } else {
//                return R.error().put("list", list);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error().put("list", list);
//        }
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if(getUserId() != Constant.SUPER_ADMIN){
//            map.put("createUserId", getUserId());
//        }
//        List<AppRoles> list = appRolesService.queryList(map);
//
        return R.success().put("list", list);
    }

}
