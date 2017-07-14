package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppMenusService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:20
 */
@RestController
@RequestMapping("app/menus")
public class AppMenusController extends AbstractController {

    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private AppMenusService appMenusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appmenu:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        int type = 1;
        if (StringUtils.isNumeric(params.get("type"))) {
            type = Integer.parseInt(params.get("type").toString());
        }
        List<AppMenu> appMenusList = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("page", query.getPage() - 1);
        map.put("page_size", query.getLimit());
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_KPI_URL, map);
                    break;
                case 2:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_ANALYSE_URL, map);
                    break;
                case 3:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_GET_APP_URL, map);
                    break;
            }
            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {

                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array == null) {
                        array = new JSONArray();
                    }
                    for (int i = 0; i < array.size(); i++) {
                        AppMenu appMenu = new AppMenu();
                        Integer id = array.getJSONObject(i).getInteger("id");
                        appMenu.setId(id);
                        appMenu.setMenuId(id);
                        switch (type) {
                            case 1:
                                String subName2=array.getJSONObject(i).getString("kpi_group");
                                appMenu.setSubName2(subName2);
                                String title = array.getJSONObject(i).getString("kpi_name");
                                appMenu.setTitle(StringUtils.isEmpty(title) ? "" : title);
                                Integer reportId = array.getJSONObject(i).getInteger("report_id");
                                appMenu.setReportId(reportId);
                                Integer templateId = array.getJSONObject(i).getInteger("template_id");
                                appMenu.setTemplateId(templateId);
                                Boolean hasAudio = array.getJSONObject(i).getBoolean("has_audio");
                                appMenu.setHasAudio(hasAudio);
                                String url = array.getJSONObject(i).getString("url");
                                appMenu.setUrl(StringUtils.isEmpty(url) ? "" : url);
                                break;
                            case 2:
                                String category = array.getJSONObject(i).getString("category");
                                appMenu.setSubName1(StringUtils.isEmpty(category) ? "" : category);
                            case 3:
                                String group_name = array.getJSONObject(i).getString("group_name");
                                appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
                                String name = array.getJSONObject(i).getString("name");
                                appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);
                                String link_path = array.getJSONObject(i).getString("link_path");
                                appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
                                break;
                        }
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

                        appMenu.setType(type);

                        String createdAt = array.getJSONObject(i).getString("created_at");
                        appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
                        String updatedAt = array.getJSONObject(i).getString("updated_at");
                        appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);
                        appMenusList.add(appMenu);
                    }
                    int total_count = jsonObject.getInteger("total_count");
                    PageUtils pageUtil = new PageUtils(appMenusList, total_count, query.getLimit(), query.getPage());

                    return R.success().put("page", pageUtil);
                } else {
                    appMenusList = new ArrayList<>();
                    PageUtils pageUtil = new PageUtils(appMenusList, 0, query.getLimit(), query.getPage());
                    String info = jsonObject.getString("message");
                    return R.error().put("page", pageUtil).setMsg(info);
                }
            } else {
                appMenusList = new ArrayList<>();
                PageUtils pageUtil = new PageUtils(appMenusList, 0, query.getLimit(), query.getPage());
                return R.error().put("page", pageUtil);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            appMenusList = new ArrayList<>();
            PageUtils pageUtil = new PageUtils(appMenusList, 0, query.getLimit(), query.getPage());
            return R.error().put("page", pageUtil);
        }
    }

    /**
     * 信息
     */
    @RequestMapping("/info")
    @RequiresPermissions("appmenu:info")
    public R info(Integer menuId, Integer type) {
//        Map<String, Object> params = new HashedMap();
//        params.put("menuId", menuId);
//        params.put("type", type);
//        AppMenu appMenu = appMenusService.queryObject(params);
//        return R.success().put("appMenu", appMenu);
        Map<String, Object> map = new HashedMap();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("id", menuId);
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_KPI_URL+"/"+menuId, map);
                    break;
                case 2:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL+"/"+menuId, map);
                    break;
                case 3:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL+"/"+menuId, map);
                    break;
            }
            System.out.println(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 200) {
                    JSONObject object = jsonObject.getJSONObject("data");
                    AppMenu appMenu = new AppMenu();
                    Integer id = object.getInteger("id");
                    appMenu.setId(id);
                    appMenu.setMenuId(id);
                    switch (type) {
                        case 1:
                            appMenu.setName("生意概况");
                            String subName2=object.getString("kpi_group");
                            appMenu.setSubName2(subName2);
                            String title = object.getString("kpi_name");
                            appMenu.setTitle(StringUtils.isEmpty(title) ? "" : title);
                            Integer reportId = object.getInteger("report_id");
                            appMenu.setReportId(reportId);
                            Integer templateId = object.getInteger("template_id");
                            appMenu.setTemplateId(templateId);
                            Boolean hasAudio = object.getBoolean("has_audio");
                            appMenu.setHasAudio(hasAudio);
                            String url = object.getString("url");
                            appMenu.setUrl(StringUtils.isEmpty(url) ? "" : url);
                            break;
                        case 2:
                            appMenu.setName("报表");
                            String category = object.getString("category");
                            appMenu.setSubName1(StringUtils.isEmpty(category) ? "" : category);
                            String group_name = object.getString("group_name");
                            appMenu.setSubName2(StringUtils.isEmpty(group_name) ? "" : group_name);
                            String name = object.getString("name");
                            appMenu.setTitle(StringUtils.isEmpty(name) ? "" : name);
                            String link_path = object.getString("link_path");
                            appMenu.setUrl(StringUtils.isEmpty(link_path) ? "" : link_path);
                            break;
                        case 3:
                            appMenu.setName("专题");
                            String a_group_name = object.getString("group_name");
                            appMenu.setSubName2(StringUtils.isEmpty(a_group_name) ? "" : a_group_name);
                            String a_name = object.getString("name");
                            appMenu.setTitle(StringUtils.isEmpty(a_name) ? "" : a_name);
                            String a_link_path = object.getString("link_path");
                            appMenu.setUrl(StringUtils.isEmpty(a_link_path) ? "" : a_link_path);
                            break;
                    }
                    String icon = object.getString("icon");
                    appMenu.setIcon(StringUtils.isEmpty(icon) ? "" : icon);
                    String icon_link = object.getString("icon_link");
                    appMenu.setIconUrl(StringUtils.isEmpty(icon_link) ? "" : icon_link);

                    String remark = object.getString("remark");
                    appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);

                    Boolean publicly = object.getBoolean("publicly");
                    if (publicly != null) {
                        appMenu.setPublicly(publicly);
                    }

                    Integer healthValue = object.getInteger("health_value");
                    if (healthValue != null) {
                        appMenu.setHealthValue(healthValue);
                    }
                    Integer groupOrder = object.getInteger("group_order");
                    if (groupOrder != null) {
                        appMenu.setGroupOrder(groupOrder);
                    }
                    Integer itemOrder = object.getInteger("item_order");
                    if (itemOrder != null) {
                        appMenu.setItemOrder(itemOrder);
                    }

                    appMenu.setType(type);

                    String createdAt = object.getString("created_at");
                    appMenu.setCreatedAt(StringUtils.isEmpty(createdAt) ? "" : createdAt);
                    String updatedAt = object.getString("updated_at");
                    appMenu.setUpdatedAt(StringUtils.isEmpty(updatedAt) ? "" : updatedAt);

                    return R.success().put("appMenu", appMenu);
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
    @RequiresPermissions("appmenu:save")
    public R save(@RequestBody AppMenu appMenu) {
//        appMenusService.save(appMenu);
//        return R.success();
        int type  =appMenu.getType();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> child = new HashedMap();
        child.put("url_path",appMenu.getUrl());
        child.put("publicly",appMenu.getPublicly());
        child.put("name",appMenu.getTitle());
        child.put("category",appMenu.getSubName1());
        child.put("group_name",appMenu.getSubName2());
        if (type==1){
            map.put("report", child);
        }else if(type==2){
            map.put("analyse", child);
        }else{
            map.put("app", child);
        }
        map.put("role_ids", appMenu.getRoleIds());

        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_KPI_URL , JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL, JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL, JSON.toJSONString(map));
                    break;
            }
            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");

//                    switch (type) {
//                        case 1:
//                            result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_REPORT_URL+"/"+appMenu.getMenuId()+"/roles" , JSON.toJSONString(map));
//                            break;
//                        case 2:
//                            result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL+"/"+appMenu.getMenuId()+"/roles", JSON.toJSONString(map));
//                            break;
//                        case 3:
//                            result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL+"/"+appMenu.getMenuId()+"/roles", JSON.toJSONString(map));
//                            break;
//                    }

                    return R.success().setMsg(info);
                } else {
                    String info = jsonObject.getString("message");
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
    @RequestMapping("/update")
    @RequiresPermissions("appmenu:update")
    public R update(@RequestBody AppMenu appMenu) {
        log.info("============>更新开始");
//        appMenusService.update(appMenu);
//        return R.success();

        int type  =appMenu.getType();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> child = new HashedMap();
        child.put("url_path",appMenu.getUrl());
        child.put("publicly",appMenu.getPublicly());
        child.put("group_name",appMenu.getSubName2());
        child.put("name",appMenu.getTitle());
        if (type==1){
            child.put("category",1);
            map.put("kpi", child);
            return null;
        }else if(type==2){
            child.put("category",appMenu.getSubName1());
            map.put("analyse", child);
        }else{

            child.put("category",1);
            map.put("app", child);
        }
        map.put("role_ids", appMenu.getRoleIdList());

        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_KPI_URL +"/"+appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL+"/"+appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL+"/"+appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
            }
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");
                    updateRole(httpUtil,appMenu.getMenuId(),type,map);
                    log.info("============>更新结束");
                    return R.success().setMsg(info);
                } else {
                    String info = jsonObject.getString("message");
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
     * 报表报表菜单权限配置
     * */
    public R updateRole(HttpMethodUtil httpUtil, Integer menuId,int type,Map<String,Object> map) {
        log.info("============>更新菜单-角色开始");
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_KPI_URL +"/"+menuId+"/roles", JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL+"/"+menuId+"/roles", JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL+"/"+menuId+"/roles", JSON.toJSONString(map));
                    break;
            }
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");
                    log.info("============>更新菜单-角色结束");
                    return R.success().setMsg(info);
                } else {
                    String info = jsonObject.getString("message");
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
    @RequiresPermissions("appmenu:delete")
    public R delete(@RequestBody Map<String, Object> params) {
//        appMenusService.deleteBatch(params);
        return R.success();
    }

    //获取某分析的菜单权限列表
    @RequestMapping("/selectRole")
    public R selectRole(@RequestParam Integer menuId, @RequestParam Map<String, Object> params) {
        int type = 1;
        if (StringUtils.isNumeric(params.get("type"))) {
            type = Integer.parseInt(params.get("type").toString());
        }
        List<AppRoles> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        map.put("lazy_load",true);
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_KPI_URL + "/" + menuId + "/roles", map);
                    break;
                case 2:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_ANALYSE_URL + "/" + menuId + "/roles", map);
                    break;
                case 3:
                    result = httpUtil.getGetResult(ConstantsUtil.AppBaseUrl.APP_BASE_POST_APP_URL + "/" + menuId + "/roles", map);
                    break;
            }
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
                        appRoles.setActive((active!=null&&active)?1:0);
                        list.add(appRoles);

                    }

                    return R.success().put("list", list);
                } else {
                    list = new ArrayList<>();
                    String info = jsonObject.getString("message");
                    return R.error().put("list", list).setMsg(info);
                }
            } else {
                list = new ArrayList<>();
                return R.error().put("list", list);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            list = new ArrayList<>();
            return R.error().put("list", list);
        }
    }

}
