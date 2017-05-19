package com.yonghui.portal.controller.sys;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;
import com.yonghui.portal.service.sys.UserAuthMenuService;
import com.yonghui.portal.service.sys.UserAuthRoleMenuService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限菜单控制类
 * Created by Shengwm on 2017/5/17.
 */
@RestController
@RequestMapping("/userAuthMenu")
public class UserAuthMenuConntroller {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserAuthMenuService userAuthMenuService;
    @Reference
    private UserAuthRoleMenuService userAuthRoleMenuService;

    // 查看用户菜单
    @RequestMapping("listRoleMenu")
    @ResponseBody
    //@IgnoreAuth
    public R listRoleMenu(HttpServletRequest request, Integer roleId, HttpServletResponse reseponse) {
        try {
            if (roleId != null) {
                RoleMenu roleMenu = userAuthRoleMenuService.getUserRoleId(roleId);
                List<Menu> menuList = new ArrayList<>();
                if (roleMenu.getMenuId().equals("0")) {
                    menuList = userAuthMenuService.listMenu();
                } else {
                    List<Integer> list = new ArrayList<Integer>();
                    for (String menuId : roleMenu.getMenuId().split(",")) {
                        list.add(Integer.parseInt(menuId));
                    }
                    menuList = userAuthMenuService.listRoleMenu(list);

                }
                log.info("角色菜单数据:" + menuList);
                return R.success().setData(menuList);
            } else {
                return R.error("roleId 为空 ！！！");
            }
        } catch (Exception e) {
            log.error("角色菜单异常", e);
            return R.error("获取角色菜单异常");
        }

    }
}

