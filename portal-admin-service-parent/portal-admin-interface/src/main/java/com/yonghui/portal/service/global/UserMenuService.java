package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.Menu;

import java.util.List;

/**
 * Created by xrr on 2017/5/31.
 */
public interface UserMenuService {

    //超级管理员菜单
    List<Menu> listMenu();

    //普通用户菜单
    List<Menu> listRoleMenu(List<Integer> list);
}
