package com.yonghui.portal.mapper.sys;

import com.yonghui.portal.model.global.Menu;

import java.util.List;

/**
 * Created by Shengwm on 2017/5/17.
 */
public interface UserAuthMenuMapper {

    List<Menu> listMenu() throws Exception;

    List<Menu> listRoleMenu(List<Integer> list) throws Exception;
}
