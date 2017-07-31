package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.init.InitProperties;
import com.yonghui.portal.model.app.AppGroups;
import com.yonghui.portal.service.app.AppGroupsService;
import com.yonghui.portal.service.global.UserAdminService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/app/groups")
public class AppGroupsController extends AbstractController {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private AppGroupsService appGroupsService;

    @Autowired
    private UserAdminService userAdminService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appgroups:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

//        List<AppGroups> appGroupsList = new ArrayList<>();
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        Map<String, Object> map = new HashedMap();
//        map.put("api_token", "api_token");
//        map.put("page", query.getPage() - 1);
//        map.put("page_size", query.getLimit());
//        if (query.get("groupName") != null) {
//            map.put("group_name", query.get("groupName"));
//        }
//        try {
//            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_GROUP_URL, map);
//
//            log.info(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 200) {
//
//                    JSONArray array = jsonObject.getJSONArray("data");
//                    if (array == null) {
//                        array = new JSONArray();
//                    }
//                    for (int i = 0; i < array.size(); i++) {
//                        AppGroups appGroups = new AppGroups();
//                        Integer id = array.getJSONObject(i).getInteger("id");
//                        appGroups.setId(id);
//                        Integer group_id = array.getJSONObject(i).getInteger("group_id");
//                        appGroups.setGroupId(group_id);
//                        String group_name = array.getJSONObject(i).getString("group_name");
//                        appGroups.setGroupName(group_name);
//                        String memo = array.getJSONObject(i).getString("memo");
//                        appGroups.setMemo(memo);
//                        String created_at = array.getJSONObject(i).getString("created_at");
//                        appGroups.setCreatedAt(created_at);
//
//                        appGroupsList.add(appGroups);
//                    }
//                    int total_count=jsonObject.getInteger("total_count");
//                    PageUtils pageUtil = new PageUtils(appGroupsList, total_count, query.getLimit(), query.getPage());
//
//                    return R.success().put("page", pageUtil);
//                } else if (jsonObject.getInteger("code") == 401) {
//                    appGroupsList = new ArrayList<>();
//                    PageUtils pageUtil = new PageUtils(appGroupsList, 0, query.getLimit(), query.getPage());
//                    String info = jsonObject.getString("message");
//                    return R.error().put("page", pageUtil).setMsg(info);
//                } else {
//                    return R.error().setMsg("APP用户查询失败");
//                }
//            } else {
//                appGroupsList = new ArrayList<>();
//                PageUtils pageUtil = new PageUtils(appGroupsList, 0, query.getLimit(), query.getPage());
//                return R.error().put("page", pageUtil);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            appGroupsList = new ArrayList<>();
//            PageUtils pageUtil = new PageUtils(appGroupsList, 0, query.getLimit(), query.getPage());
//            return R.error().put("page", pageUtil);
//        }

        List<AppGroups> appGroupsList = appGroupsService.queryList(query);
        int total = appGroupsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appGroupsList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appgroups:info")
    public R info(@PathVariable("id") Integer id) {
        AppGroups appGroups = appGroupsService.queryObject(id);
        return R.success().put("appGroups", appGroups);
//        Map<String, Object> map = new HashedMap();
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        map.put("api_token", "api_token");
//        map.put("id", id);
//        try {
//            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_GROUP_URL + "/" + id, map);
//
//            log.info(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 200) {
//
//                    JSONObject obj = jsonObject.getJSONObject("data");
//                    AppGroups appGroups = new AppGroups();
//                    Integer xid = obj.getInteger("id");
//                    appGroups.setId(xid);
//                    Integer group_id = obj.getInteger("group_id");
//                    appGroups.setGroupId(group_id);
//                    String group_name = obj.getString("group_name");
//                    appGroups.setGroupName(group_name);
//                    String memo = obj.getString("memo");
//                    appGroups.setMemo(memo);
////                    String created_at = obj.getString("created_at");
////                    appGroups.setCreatedAt(created_at);
//
//                    return R.success().put("appGroups", appGroups);
//                } else {
//                    String info = jsonObject.getString("message");
//                    return R.error().setMsg(info);
//                }
//            } else {
//                return R.error().setMsg("APP查询失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error().setMsg("APP查询失败");
//        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appgroups:save")
    public R save(@RequestBody AppGroups appGroups) {
//        appGroupsService.save(appGroups);
//        return R.success();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("group_id", appGroups.getGroupId());
        appMap.put("group_name", appGroups.getGroupName());
        appMap.put("memo", appGroups.getMemo());
        map.put("group", appMap);
        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_GROUP_URL, JSON.toJSONString(map));
            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    return R.success();
                } else if (jsonObject.getInteger("code") == 200) {
                    String info = jsonObject.getString("message");
                    return R.error().setMsg(info);
                } else {
                    return R.error().setMsg("APP群组添加失败");
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
    @RequiresPermissions("appgroups:update")
    public R update(@RequestBody AppGroups appGroups) {
//        appGroupsService.update(appGroups);
//        return R.success();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> appMap = new HashedMap();
        appMap.put("group_id", appGroups.getGroupId());
        appMap.put("group_name", appGroups.getGroupName());
        appMap.put("memo", appGroups.getMemo());
        map.put("group", appMap);
        try {
            String result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_GROUP_URL+ "/" + appGroups.getId(), JSON.toJSONString(map));

            log.info(result);
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
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appgroups:delete")
    public R delete(@RequestBody Integer[] ids) {
//        appGroupsService.deleteBatch(ids);
        return R.success();
    }


    /**
     * 列表
     */
    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> params) {
        String areaMans=params.get("areaMans")+"";
        //查询列表数据
        List<AppGroups> appGroupsList = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("page", 0);
        if (!StringUtils.isEmpty(areaMans)){
            if (areaMans.equals("ALL")){
                areaMans="全部";
            }
            map.put("group_name", areaMans);
            map.put("page_size", 1000);
        }else {
            map.put("page_size", 10000);
        }
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_GROUP_URL, map);

            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    for (int i = 0; i < array.size(); i++) {
                        AppGroups appGroups = new AppGroups();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appGroups.setId(id);
                        Integer group_id = array.getJSONObject(i).getInteger("group_id");
                        appGroups.setGroupId(group_id);
                        String group_name = array.getJSONObject(i).getString("group_name");
                        appGroups.setGroupName(group_name);
                        String memo = array.getJSONObject(i).getString("memo");
                        appGroups.setMemo(memo);
                        String created_at = array.getJSONObject(i).getString("created_at");
                        appGroups.setCreatedAt(created_at);
                        appGroups.setActive(0);
                        appGroupsList.add(appGroups);
                    }

                    return R.success().put("groupList", appGroupsList);
                } else if (jsonObject.getInteger("code") == 401) {
                    appGroupsList = new ArrayList<>();
                    String info = jsonObject.getString("message");
                    return R.success().put("groupList", appGroupsList).setMsg(info);
                } else {
                    return R.error().setMsg("APP用户查询失败");
                }
            } else {
                appGroupsList = new ArrayList<>();
                return R.success().put("groupList", appGroupsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appGroupsList = new ArrayList<>();
            return R.success().put("groupList", appGroupsList);
        }

    }
    /**
     * 列表
     */
    @RequestMapping("/selectGroups")
    public R selectGroups(@RequestParam String userNum) {
        List<AppGroups> groupList = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
       // map.put("lazy_load", true);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_USER_URL + "/" + userNum + "/groups", map);
            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    for (int i = 0; i < array.size(); i++) {
                        AppGroups appGroups = new AppGroups();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appGroups.setId(id);
                        Integer group_id = array.getJSONObject(i).getInteger("group_id");
                        if (group_id != null) {
                            appGroups.setGroupId(group_id);
                        }
                        String group_name = array.getJSONObject(i).getString("group_name");
                        appGroups.setGroupName(StringUtils.isEmpty(group_name) ? "" : group_name);
                        String memo = array.getJSONObject(i).getString("memo");
                        appGroups.setMemo(StringUtils.isEmpty(memo) ? "" : memo);
                        String created_at = array.getJSONObject(i).getString("created_at");
                        appGroups.setCreatedAt(StringUtils.isEmpty(created_at) ? "" : created_at);
                        Boolean active = array.getJSONObject(i).getBoolean("active");
                        appGroups.setActive((active != null && active) ? 1 : 0);
                        groupList.add(appGroups);
                    }
                    return R.success().put("groupList", groupList);
                } else {
                    groupList = new ArrayList<>();
                    return R.success().put("groupList", groupList);
                }
            } else {
                groupList = new ArrayList<>();
                return R.success().put("groupList", groupList);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            return R.success().put("groupList", groupList);
        }
    }
}
