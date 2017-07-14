package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.model.app.AppUsers;
import com.yonghui.portal.service.app.AppRolesService;
import com.yonghui.portal.service.app.AppUserRolesService;
import com.yonghui.portal.service.app.AppUsersService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
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
 * @date 2017-05-22 17:27:18
 */
@RestController
@RequestMapping("/app/users")
public class AppUsersController extends AbstractController {

    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private AppUsersService appUsersService;
    @Autowired
    private AppRolesService appRolesService;
    @Autowired
    private AppUserRolesService appUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appusers:list")
    public R list(@RequestParam Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);

//        List<AppUsers> appUsersList = appUsersService.queryList(query);
        List<AppUsers> appUsersList = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("page", query.getPage() - 1);
        map.put("page_size", query.getLimit());
        try {
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_USER_URL, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {
                    log.info(jsonObject.toJSONString());

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    for (int i = 0; i < array.size(); i++) {
                        AppUsers appUsers = new AppUsers();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appUsers.setId(id);
                        String user_name = array.getJSONObject(i).getString("user_name");
                        appUsers.setUserName(user_name);
                        String user_num = array.getJSONObject(i).getString("user_num");
                        appUsers.setUserNum(user_num);
                        String user_pass = array.getJSONObject(i).getString("user_pass");
                        appUsers.setUserPass(user_pass);
//                        String status=array.getJSONObject(i).getString("status");
                        String email = array.getJSONObject(i).getString("email");
                        appUsers.setEmail(email);
                        String mobile = array.getJSONObject(i).getString("mobile");
                        appUsers.setMobile(mobile);
                        String tel = array.getJSONObject(i).getString("tel");
                        appUsers.setTel(tel);
                        Date join_date = array.getJSONObject(i).getDate("join_date");
                        appUsers.setJoinDate(join_date);
                        String position = array.getJSONObject(i).getString("position");
                        appUsers.setPosition(position);
                        appUsersList.add(appUsers);
                    }
                    int total_count = jsonObject.getInteger("total_count");
                    PageUtils pageUtil = new PageUtils(appUsersList, total_count, query.getLimit(), query.getPage());

                    return R.success().put("page", pageUtil);
                } else if (jsonObject.getInteger("code") == 401) {
                    log.error(jsonObject.toJSONString());
                    appUsersList = new ArrayList<>();
                    PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
                    String info = jsonObject.getString("message");
                    return R.error().put("page", pageUtil).setMsg(info);
                } else {
                    log.error(jsonObject.toJSONString());
                    return R.error().setMsg("APP用户查询失败");
                }
            } else {
                appUsersList = new ArrayList<>();
                PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
                return R.error().put("page", pageUtil);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appUsersList = new ArrayList<>();
            PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
            return R.error().put("page", pageUtil);
        }

//        int total = appUsersService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(appUsersList, total, query.getLimit(), query.getPage());
//
//        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{userNum}")
    @RequiresPermissions("appusers:info")
    public R info(@PathVariable("userNum") String userNum) {
//        AppUsers appUsers = appUsersService.queryObject(id);
//        return R.success().put("appUsers", appUsers);
        List<AppUsers> appUsersList = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        try {
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL + "/" + userNum, map);

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {
                    log.info(jsonObject.toJSONString());

                    AppUsers appUsers = new AppUsers();
                    Integer id = jsonObject.getInteger("id");
                    appUsers.setId(id);
                    String user_name = jsonObject.getString("user_name");
                    appUsers.setUserName(user_name);
                    String user_num = jsonObject.getString("user_num");
                    appUsers.setUserNum(user_num);
                    String user_pass = jsonObject.getString("user_pass");
                    appUsers.setUserPass(user_pass);
                    Integer group_id = jsonObject.getInteger("group_id");
                    appUsers.setGroupId(group_id);
//                        String status=jsonObject.getString("status");
                    String email = jsonObject.getString("email");
                    appUsers.setEmail(email);
                    String mobile = jsonObject.getString("mobile");
                    appUsers.setMobile(mobile);
                    String tel = jsonObject.getString("tel");
                    appUsers.setTel(tel);
                    Date join_date = jsonObject.getDate("join_date");
                    appUsers.setJoinDate(join_date);
                    String position = jsonObject.getString("position");
                    appUsers.setPosition(position);
                    appUsersList.add(appUsers);
                    return selectRole(userNum, appUsers);
                } else if (jsonObject.getInteger("code") == 401) {
                    log.error(jsonObject.toJSONString());
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    log.error(jsonObject.toJSONString());
                    return R.error().setMsg("APP用户查询失败");
                }
            } else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    public R selectRole(String userNum, AppUsers appUsers) {
        List<AppRoles> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("lazy_load", true);
        try {
            String result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL + "/" + userNum + "/roles", map);
            log.info(result);
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
                        if (role_id != null) {
                            appRoles.setRoleId(role_id);
                        }
                        String role_name = array.getJSONObject(i).getString("role_name");
                        appRoles.setRoleName(StringUtils.isEmpty(role_name) ? "" : role_name);
                        String memo = array.getJSONObject(i).getString("memo");
                        appRoles.setMemo(StringUtils.isEmpty(memo) ? "" : memo);
                        String created_at = array.getJSONObject(i).getString("created_at");
                        appRoles.setCreatedAt(StringUtils.isEmpty(created_at) ? "" : created_at);
                        Boolean active = array.getJSONObject(i).getBoolean("active");
                        appRoles.setActive((active != null && active) ? 1 : 0);
                        list.add(appRoles);
                    }
                    appUsers.setRoleList(list);
                    return R.success().put("appUsers", appUsers);
                } else {
                    list = new ArrayList<>();
                    appUsers.setRoleList(list);
                    return R.success().put("appUsers", appUsers);
                }
            } else {
                list = new ArrayList<>();
                appUsers.setRoleList(list);
                return R.success().put("appUsers", appUsers);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            appUsers.setRoleList(list);
            return R.success().put("appUsers", appUsers);
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appusers:save")
    public R save(@RequestBody AppUsers appUsers) {
        appUsers.setUserPass(Md5Util.getMd5("MD5", 0, null, "123456"));
//		appUsersService.save(appUsers);
//        appUserRoleService.saveOrUpdate(appUsers.getId(),appUsers.getRoleIdList());
//        return R.success();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("user_name", appUsers.getUserName());
        appMap.put("user_num", appUsers.getUserNum());
        appMap.put("user_pass", appUsers.getUserPass());
        appMap.put("email", appUsers.getEmail());
        appMap.put("mobile", appUsers.getMobile());
        appMap.put("tel", appUsers.getTel());
        appMap.put("join_date", appUsers.getJoinDate());
        appMap.put("position", appUsers.getPosition());
        map.put("user", appMap);

        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL, JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");
                    map.clear();
                    map.put("api_token", "api_token");
                    map.put("role_ids", appUsers.getRoleIdList());
                    updateRole(httpUtil, appUsers.getUserNum(), map);
                    map.clear();
                    map.put("api_token", "api_token");
                    map.put("group_ids", new ArrayList<>().add(appUsers.getGroupId()));
                    updateRole(httpUtil, appUsers.getUserNum(), map);
                    return R.success().setMsg(info);
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP用户添加失败");
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
    @RequiresPermissions("appusers:update")
    public R update(@RequestBody AppUsers appUsers) {
//		appUsersService.update(appUsers);
//        appUserRoleService.saveOrUpdate(appUsers.getId(),appUsers.getRoleIdList());
//        return R.success();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("user_name", appUsers.getUserName());
        appMap.put("user_num", appUsers.getUserNum());
        appMap.put("email", appUsers.getEmail());
        appMap.put("mobile", appUsers.getMobile());
        appMap.put("tel", appUsers.getTel());
        appMap.put("join_date", appUsers.getJoinDate());
        map.put("user", appMap);

        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL + "/" + appUsers.getUserNum(), JSON.toJSONString(map));

            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");
                    map.clear();
                    map.put("api_token", "api_token");
                    map.put("role_ids", appUsers.getRoleIdList());
                    updateRole(httpUtil, appUsers.getUserNum(), map);
                    map.clear();
                    map.put("api_token", "api_token");
                    List<Integer> group_ids = new ArrayList<Integer>();
                    group_ids.add(appUsers.getGroupId());
                    map.put("group_ids", group_ids);
                    updateGroup(httpUtil, appUsers.getUserNum(), map);
                    return R.success().setMsg(info);
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP用户添加失败");
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
     * 用户角色分配
     */
    public void updateRole(HttpMethodUtil httpUtil, String userNum, Map<String, Object> map) throws Exception {
        log.info("============>更新用户-角色开始");
        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL + "/" + userNum + "/roles", JSON.toJSONString(map));
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    log.info("============>更新用户-角色结束");
                } else {
                    String info = jsonObject.getString("message");
                    throw new Exception(info);
                }
            } else {
                throw new Exception("更新用户角色失败");
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 用户角色分配
     */
    public void updateGroup(HttpMethodUtil httpUtil, String userNum, Map<String, Object> map) throws Exception {
        log.info("============>更新用户-群组开始");
        try {
            String result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_USER_URL + "/" + userNum + "/groups", JSON.toJSONString(map));
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    log.info("============>更新用户-群组结束");
                } else {
                    String info = jsonObject.getString("message");
                    throw new Exception(info);
                }
            } else {
                throw new Exception("更新用户群组失败");
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appusers:delete")
    public R delete(@RequestBody Integer[] ids) {
//		appUsersService.deleteBatch(ids);
        return R.success();
    }

}
