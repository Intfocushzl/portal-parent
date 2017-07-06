package com.yonghui.portal.controller.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppGroups;
import com.yonghui.portal.service.app.AppGroupsService;
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
@RequestMapping("/app/groups")
public class AppGroupsController extends AbstractController {

    public static final String APP_BASE_GROUP_URL = "http://yonghui-test.idata.mobi/api/v2/group";

    @Autowired
    private AppGroupsService appGroupsService;

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
//        try {
//            String result = httpUtil.getGetResult(AppGroupsController.APP_BASE_GROUP_URL + "s", map);
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
//                        AppGroups appGroups = new AppGroups();
//                        Integer id = array.getJSONObject(i).getInteger("id");
//                        appGroups.setId(id);
//                        String group_name = array.getJSONObject(i).getString("group_name");
//                        appGroups.setGroupName(group_name);
//                        String memo = array.getJSONObject(i).getString("memo");
//                        appGroups.setMemo(memo);
//                        Date created_at = array.getJSONObject(i).getDate("created_at");
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
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appgroups:save")
    public R save(@RequestBody AppGroups appGroups) {
        appGroupsService.save(appGroups);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appgroups:update")
    public R update(@RequestBody AppGroups appGroups) {
        appGroupsService.update(appGroups);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appgroups:delete")
    public R delete(@RequestBody Integer[] ids) {
        appGroupsService.deleteBatch(ids);
        return R.success();
    }

}
