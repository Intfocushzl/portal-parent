package com.yonghui.portal.service.sys;

import com.yonghui.portal.model.global.Menu;

import java.util.List;

/**
 * Created by Shengwm on 2017/5/17.
 */
public interface UserAuthMenuService {
    //超级管理员菜单
    List<Menu> listMenu();

    //普通用户菜单
    List<Menu> listRoleMenu(List<Integer> list);

}
