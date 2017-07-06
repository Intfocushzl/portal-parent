package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppRolesService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    public static final String APP_BASE_ROLE_URL = "http://yonghui-test.idata.mobi/api/v2/role";

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

        List<AppRoles> appRolesList = appRolesService.queryList(query);
        int total = appRolesService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appRolesList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);

//        List<AppRoles> appRolesList = new ArrayList<>();
//
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        Map<String, Object> map = new HashedMap();
//        map.put("api_token", "api_token");
//        map.put("page", query.getPage() - 1);
//        map.put("page_size", query.getLimit());
//        try {
//            String result = httpUtil.getGetResult(AppRolesController.APP_BASE_ROLE_URL + "s", map);
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
//                    for (int i = 0; i < array.size(); i++) {
//                        AppRoles appRoles = new AppRoles();
//                        Integer id = array.getJSONObject(i).getInteger("id");
//                        appRoles.setId(id);
//                        String role_name = array.getJSONObject(i).getString("role_name");
//                        appRoles.setRoleName(role_name);
//                        String memo = array.getJSONObject(i).getString("memo");
//                        appRoles.setMemo(memo);
//                        Date created_at = array.getJSONObject(i).getDate("created_at");
//                        appRoles.setCreatedAt(created_at);
//                        appRolesList.add(appRoles);
//                    }
//                    int total_count=jsonObject.getInteger("total_count");
//                    PageUtils pageUtil = new PageUtils(appRolesList, total_count, query.getLimit(), query.getPage());
//
//                    return R.success().put("page", pageUtil);
//                } else if (jsonObject.getInteger("code") == 401) {
//                    appRolesList = new ArrayList<>();
//                    PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
//                    String info = jsonObject.getString("message");
//                    return R.error().put("page", pageUtil).setMsg(info);
//                } else {
//                    return R.error().setMsg("APP用户查询失败");
//                }
//            } else {
//                appRolesList = new ArrayList<>();
//                PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
//                return R.error().put("page", pageUtil);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            appRolesList = new ArrayList<>();
//            PageUtils pageUtil = new PageUtils(appRolesList, 0, query.getLimit(), query.getPage());
//            return R.error().put("page", pageUtil);
//        }
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
//    @RequiresPermissions("app:role:select")
    public R select(@RequestParam Map<String, Object> map) {
//        List<AppRoles> list = new ArrayList<>();
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        map.put("api_token", "api_token");
//        map.put("page_size", 100);
//        try {
//            String result = httpUtil.getGetResult(AppUsersController.APP_BASE_USER_URL + "s", map);
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
//                        AppRoles appRoles = new AppRoles();
//                        Integer id = array.getJSONObject(i).getInteger("id");
//                        appRoles.setId(id);
//                        String role_name = array.getJSONObject(i).getString("role_name");
//                        appRoles.setRoleName(role_name);
//                        String memo = array.getJSONObject(i).getString("memo");
//                        appRoles.setMemo(memo);
//                        list.add(appRoles);
//                    }
//
//                    return R.success().put("list", list);
//                } else if (jsonObject.getInteger("code") == 401) {
//                    String info = jsonObject.getString("message");
//                    return R.error().put("list", list).setMsg(info);
//                } else {
//                    return R.error().setMsg("APP用户查询失败");
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
        List<AppRoles> list = appRolesService.queryList(map);

        return R.success().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("approles:info")
    public R info(@PathVariable("id") Integer id) {
        AppRoles appRoles = appRolesService.queryObject(id);

        //查询角色对应的菜单
        List<Map<String,Object>> menuList = appRolesService.queryMenuList(id);
        appRoles.setMenuList(menuList);

        return R.success().put("appRoles", appRoles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("approles:save")
    public R save(@RequestBody AppRoles appRoles) {
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        Map<String, Object> map = new HashedMap();
//        map.put("api_token", "api_token");
//        Map<String,Object> appMap=new HashedMap();
//        appMap.put("role_name",appRoles.getRoleName());
//        appMap.put("memo",appRoles.getMemo());
//        map.put("role",appMap);
//        try {
//            String result = httpUtil.getPostJsonResult(AppRolesController.APP_BASE_ROLE_URL , JSON.toJSONString(map));
//
//            System.out.println(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 201) {
//                    return R.success();
//                } else if (jsonObject.getInteger("code") == 200) {
//                    String info = jsonObject.getString("message");
//                    return R.error().setMsg(info);
//                } else {
//                    return R.error().setMsg("APP角色添加失败");
//                }
//            } else {
//                return R.error();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error();
//        }
        appRolesService.save(appRoles);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("approles:update")
    public R update(@RequestBody AppRoles appRoles) {
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        Map<String, Object> map = new HashedMap();
//        map.put("api_token", "api_token");
//        Map<String,Object> appMap=new HashedMap();
//        appMap.put("role_name",appRoles.getRoleName());
//        appMap.put("memo",appRoles.getMemo());
//        map.put("role",appMap);
//        try {
//            String result = httpUtil.getDeleteResult(AppRolesController.APP_BASE_ROLE_URL+"/"+appRoles.getId() ,map);
//
//            System.out.println(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 201) {
//                    return R.success();
//                } else if (jsonObject.getInteger("code") == 200) {
//                    String info = jsonObject.getString("message");
//                    return R.error().setMsg(info);
//                } else {
//                    return R.error().setMsg("APP角色删除失败");
//                }
//            } else {
//                return R.error();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error();
//        }
        appRolesService.update(appRoles);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("approles:delete")
    public R delete(@RequestBody Integer[] ids) {
        appRolesService.deleteBatch(ids);
        return R.success();
    }


}
