package com.yonghui.portal.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户权限菜单控制类
 * Created by Shengwm on 2017/5/17.
 */
@RestController
@RequestMapping("/userAuthMenu")
public class UserAuthMenuConntroller {

    Logger log = Logger.getLogger(this.getClass());

    /*@Reference
    private UserAuthMenuService userAuthMenuService;
    @Reference
    private UserAuthRoleMenuService userAuthRoleMenuService;*/
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;

    // 查看用户菜单
    @RequestMapping("listRoleMenu")
    @ResponseBody
    public JSONObject listRoleMenu(HttpServletRequest request, Integer roleId, HttpServletResponse reseponse) {

        String menuJson = redisBizUtilApi.getRoleMenu(roleId);
        JSONObject json = new JSONObject();
        if(StringUtils.isEmpty(menuJson)){
            json.put("msg","请求成功，该角色没有菜单");
            json.put("code","0");
            json.put("date",null);
        }else{
            json = JSONObject.parseObject(menuJson);
            json.put("msg","请求成功");
            json.put("code","0");
        }
        return json;
        /*try {
            String str;
            List<Menu> menus = new ArrayList<>();
            if (roleId != null) {
                RoleMenu roleMenu = userAuthRoleMenuService.getUserRoleId(roleId);
                List<Menu> menuList = new ArrayList<>();
                if (roleMenu.getMenuId().equals("0")) {
                    menuList = userAuthMenuService.listMenu();
                    menus = new ListToTreeUtils().listTreeMenu(menuList);
                } else {
                    List<Integer> list = new ArrayList<Integer>();
                    for (String menuId : roleMenu.getMenuId().split(",")) {
                        list.add(Integer.parseInt(menuId));
                    }
                    menuList = userAuthMenuService.listRoleMenu(list);
                    menus = new ListToTreeUtils().listTreeMenu(menuList);

                }
                log.info("角色菜单数据:" + menuList);
                return R.success().setData(menus);
            } else {
                return R.error("roleId 为空 ！！！");
            }
        } catch (Exception e) {
            log.error("角色菜单异常", e);
            return R.error("获取角色菜单异常");
        }*/

    }
}

