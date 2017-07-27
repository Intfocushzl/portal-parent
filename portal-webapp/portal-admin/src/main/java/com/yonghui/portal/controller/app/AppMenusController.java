package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.init.InitProperties;
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
                    if (query.get("subName2") != null) {
                        map.put("kpi_group", query.get("subName2"));
                    }
                    if (query.get("title") != null) {
                        map.put("kpi_name", query.get("title"));
                    }
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_KPI_URL, map);
                    break;
                case 2:
                    if (query.get("subName2") != null) {
                        map.put("group_name", query.get("subName2"));
                    }
                    if (query.get("title") != null) {
                        map.put("name", query.get("title"));
                    }
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_ANALYSE_URL, map);
                    break;
                case 3:
                    if (query.get("subName2") != null) {
                        map.put("group_name", query.get("subName2"));
                    }
                    if (query.get("title") != null) {
                        map.put("name", query.get("title"));
                    }
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_APP_URL, map);
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
                                String subName2 = array.getJSONObject(i).getString("kpi_group");
                                appMenu.setSubName2(subName2);
                                String title = array.getJSONObject(i).getString("kpi_name");
                                appMenu.setTitle(StringUtils.isEmpty(title) ? "" : title);
                                Integer kpiId = array.getJSONObject(i).getInteger("kpi_id");
                                appMenu.setKpiId(kpiId);
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
     * 角色对应菜单
     */
    @RequestMapping("/selectKpisMenu")
    public R selectKpisMenu(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("page", 0);
        map.put("page_size", 10000);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_KPI_URL, map);

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
    public R selectAnalysesMenu(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("page", 0);
        map.put("page_size", 10000);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_ANALYSE_URL, map);

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
    public R selectAppsMenu(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();

        HttpMethodUtil httpUtil = new HttpMethodUtil();
        map.put("api_token", "api_token");
        map.put("page", 0);
        map.put("page_size", 10000);
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_APP_URL, map);

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
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_KPI_URL + "/" + menuId, map);
                    break;
                case 2:
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ANALYSE_URL + "/" + menuId, map);
                    break;
                case 3:
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_APP_URL + "/" + menuId, map);
                    break;
            }
            log.info(result);
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
                            String subName2 = object.getString("kpi_group");
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
        int type = appMenu.getType();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> child = new HashedMap();
        if (type == 1) {
            child.put("publicly", appMenu.getPublicly());
            child.put("kpi_name", appMenu.getTitle());
            child.put("kpi_group", appMenu.getSubName2());
            child.put("kpi_id", appMenu.getKpiId() == null ? 0 : appMenu.getKpiId());
            child.put("link", 0);
            map.put("kpi", child);
        } else if (type == 2) {
            child.put("publicly", appMenu.getPublicly());
            child.put("name", appMenu.getTitle());
            child.put("group_name", appMenu.getSubName2());
            child.put("category", appMenu.getSubName1());
            child.put("url_path", appMenu.getUrl());
            map.put("analyse", child);
        } else {
            child.put("publicly", appMenu.getPublicly());
            child.put("name", appMenu.getTitle());
            child.put("group_name", appMenu.getSubName2());
            child.put("category", 1);
            child.put("url_path", appMenu.getUrl());
            map.put("app", child);
        }
        map.put("role_ids", appMenu.getRoleIds());

        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_KPI_URL, JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ANALYSE_URL, JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_APP_URL, JSON.toJSONString(map));
                    break;
            }
            log.info(result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");

//                    switch (type) {
//                        case 1:
//                            result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_REPORT_URL+"/"+appMenu.getMenuId()+"/roles" , JSON.toJSONString(map));
//                            break;
//                        case 2:
//                            result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ANALYSE_URL+"/"+appMenu.getMenuId()+"/roles", JSON.toJSONString(map));
//                            break;
//                        case 3:
//                            result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_APP_URL+"/"+appMenu.getMenuId()+"/roles", JSON.toJSONString(map));
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

        int type = appMenu.getType();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        Map<String, Object> child = new HashedMap();
        if (type == 1) {
            child.put("publicly", appMenu.getPublicly());
            child.put("kpi_name", appMenu.getTitle());
            child.put("kpi_group", appMenu.getSubName2());
            child.put("kpi_id", 0);
            child.put("link", 0);
            map.put("kpi", child);
        } else if (type == 2) {
            child.put("publicly", appMenu.getPublicly());
            child.put("name", appMenu.getTitle());
            child.put("group_name", appMenu.getSubName2());
            child.put("category", appMenu.getSubName1());
            child.put("url_path", appMenu.getUrl());
            map.put("analyse", child);
        } else {
            child.put("publicly", appMenu.getPublicly());
            child.put("name", appMenu.getTitle());
            child.put("group_name", appMenu.getSubName2());
            child.put("category", 1);
            child.put("url_path", appMenu.getUrl());
            map.put("app", child);
        }
        map.put("role_ids", appMenu.getRoleIdList());

        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_KPI_URL + "/" + appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ANALYSE_URL + "/" + appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_APP_URL + "/" + appMenu.getMenuId(), JSON.toJSONString(map));
                    break;
            }
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code") == 201) {
                    String info = jsonObject.getString("message");
                    updateRole(httpUtil, appMenu.getMenuId(), type, map);
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
     */
    public R updateRole(HttpMethodUtil httpUtil, Integer menuId, int type, Map<String, Object> map) {
        log.info("============>更新菜单-角色开始");
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_KPI_URL + "/" + menuId + "/roles", JSON.toJSONString(map));
                    break;
                case 2:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_ANALYSE_URL + "/" + menuId + "/roles", JSON.toJSONString(map));
                    break;
                case 3:
                    result = httpUtil.getPostJsonResult(InitProperties.APP_BASE_POST_APP_URL + "/" + menuId + "/roles", JSON.toJSONString(map));
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
        map.put("lazy_load", true);
        try {
            String result = "";
            switch (type) {
                case 1:
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_KPI_URL + "/" + menuId + "/roles", map);
                    break;
                case 2:
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_ANALYSE_URL + "/" + menuId + "/roles", map);
                    break;
                case 3:
                    result = httpUtil.getGetResult(InitProperties.APP_BASE_POST_APP_URL + "/" + menuId + "/roles", map);
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
                        appRoles.setActive((active != null && active) ? 1 : 0);
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

    //获取报表列表
    @RequestMapping("/selectReport")
    public R selectReport() {
        List<AppMenu> list = new ArrayList<>();
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        Map<String, Object> map = new HashedMap();
        map.put("api_token", "api_token");
        try {
            String result = httpUtil.getGetResult(InitProperties.APP_BASE_GET_REPORT_URL, map);
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
                        String title = array.getJSONObject(i).getString("title");
                        appMenu.setTitle(StringUtils.isEmpty(title) ? "" : title);
                        Integer kpi_id = array.getJSONObject(i).getInteger("kpi_id");
                        appMenu.setKpiId(kpi_id);
                        Integer reportId = array.getJSONObject(i).getInteger("report_id");
                        appMenu.setReportId(reportId);
                        Integer templateId = array.getJSONObject(i).getInteger("template_id");
                        appMenu.setTemplateId(templateId);
                        Boolean hasAudio = array.getJSONObject(i).getBoolean("has_audio");
                        appMenu.setHasAudio(hasAudio);
                        String remark = array.getJSONObject(i).getString("remark");
                        appMenu.setRemark(StringUtils.isEmpty(remark) ? "" : remark);
                        list.add(appMenu);
                    }
                    Map<String, Object> res = new HashedMap();
                    res.put("list", list);
                    return R.success().setData(res);
                } else {
                    Map<String, Object> res = new HashedMap();
                    res.put("list", list);
                    String info = jsonObject.getString("message");
                    return R.error().setData(res).setMsg(info);
                }
            } else {
                list = new ArrayList<>();
                Map<String, Object> res = new HashedMap();
                res.put("list", list);
                return R.error().setData(res);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            list = new ArrayList<>();
            Map<String, Object> res = new HashedMap();
            res.put("list", list);
            return R.error().setData(res);
        }
    }

}
