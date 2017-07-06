package com.yonghui.portal.controller.app;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.model.app.AppUsers;
import com.yonghui.portal.service.app.AppRolesService;
import com.yonghui.portal.service.app.AppUserRolesService;
import com.yonghui.portal.service.app.AppUsersService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:18
 */
@RestController
@RequestMapping("/app/users")
public class AppUsersController extends AbstractController {

    Logger log = Logger.getLogger(this.getClass());
    public static final String APP_BASE_USER_URL = "http://yonghui-test.idata.mobi/api/v2/user";

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
    public R list(@RequestParam Map<String, Object> params){

        //查询列表数据
        Query query = new Query(params);

        List<AppUsers> appUsersList = appUsersService.queryList(query);
//        List<AppUsers> appUsersList = new ArrayList<>();
//
//        HttpMethodUtil httpUtil = new HttpMethodUtil();
//        Map<String,Object> map=new HashedMap();
//        map.put("api_token","api_token");
//        map.put("page",query.getPage()-1);
//        map.put("page_size",query.getLimit());
//        try {
//            String result  = httpUtil.getGetResult(AppUsersController.APP_BASE_USER_URL+"s", map);
//
//            System.out.println(result);
//            if (!StringUtils.isEmpty(result)) {
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                if (jsonObject.getInteger("code") == 200) {
//                    log.info(jsonObject.toJSONString());
//
//                    JSONArray array=jsonObject.getJSONArray("data");
//                    if (array==null){
//                        array=new JSONArray();
//                    }
//                    for (int i=0;i<array.size();i++){
//                        AppUsers appUsers=new AppUsers();
//                        Integer id=array.getJSONObject(i).getInteger("id");
//                        appUsers.setId(id);
//                        String user_name=array.getJSONObject(i).getString("user_name");
//                        appUsers.setUserName(user_name);
//                        String user_num=array.getJSONObject(i).getString("user_num");
//                        appUsers.setUserNum(user_num);
//                        String user_pass=array.getJSONObject(i).getString("user_pass");
//                        appUsers.setUserPass(user_pass);
////                        String status=array.getJSONObject(i).getString("status");
//                        String email=array.getJSONObject(i).getString("email");
//                        appUsers.setEmail(email);
//                        String mobile=array.getJSONObject(i).getString("mobile");
//                        appUsers.setMobile(mobile);
//                        String tel=array.getJSONObject(i).getString("tel");
//                        appUsers.setTel(tel);
//                        Date join_date=array.getJSONObject(i).getDate("join_date");
//                        appUsers.setJoinDate(join_date);
//                        String position=array.getJSONObject(i).getString("position");
//                        appUsers.setPosition(position);
//                        appUsersList.add(appUsers);
//                    }
//                    int total_count=jsonObject.getInteger("total_count");
//                    PageUtils pageUtil = new PageUtils(appUsersList, total_count, query.getLimit(), query.getPage());
//
//                    return R.success().put("page", pageUtil);
//                } else if (jsonObject.getInteger("code") == 401) {
//                    log.error(jsonObject.toJSONString());
//                    appUsersList=new ArrayList<>();
//                    PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
//                    String info=jsonObject.getString("message");
//                    return R.error().put("page", pageUtil).setMsg(info);
//                } else {
//                    log.error(jsonObject.toJSONString());
//                    return R.error().setMsg("APP用户查询失败");
//                }
//            }else {
//                appUsersList=new ArrayList<>();
//                PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
//                return R.error().put("page", pageUtil);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            appUsersList=new ArrayList<>();
//            PageUtils pageUtil = new PageUtils(appUsersList, 0, query.getLimit(), query.getPage());
//            return R.error().put("page", pageUtil);
//        }

        int total = appUsersService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(appUsersList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }


//    {
//        api_token: '必填项，具体机制可参考上述相关说明',
//                page: '可选，第几页，默认 0',
//            limit: '可选，每页数据项数量，默认 15'
//    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appusers:info")
    public R info(@PathVariable("id") Integer id){
        AppUsers appUsers = appUsersService.queryObject(id);
        return R.success().put("appUsers", appUsers);
    }

    /*获取角色列表*/
    @RequestMapping("/roleSelect/{id}")
    public R roleSelect(@PathVariable("id") Integer id){
        List<AppRoles> appUsersList = appRolesService.queryList(new HashedMap());
       List<Integer> list = appUserRoleService.queryRoleIdList(id);
        for (AppRoles appRole : appUsersList) {
            for (Integer i : list) {
                if (appRole.getId() == i) {
                    appRole.setActive(1);
                }
            }
        }
        return R.success().put("list",appUsersList);
    }


        /**
         * 保存
         */
    @RequestMapping("/save")
    @RequiresPermissions("appusers:save")
    public R save(@RequestBody AppUsers appUsers){
        appUsers.setUserPass(Md5Util.getMd5("MD5", 0, null, "123456"));
		appUsersService.save(appUsers);
        appUserRoleService.saveOrUpdate(appUsers.getId(),appUsers.getRoleIdList());
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appusers:update")
    public R update(@RequestBody AppUsers appUsers){
		appUsersService.update(appUsers);
        appUserRoleService.saveOrUpdate(appUsers.getId(),appUsers.getRoleIdList());
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appusers:delete")
    public R delete(@RequestBody Integer[] ids){
		appUsersService.deleteBatch(ids);
        return R.success();
    }

}
