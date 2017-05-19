package com.yonghui.portal.mapper.sys;

import com.yonghui.portal.model.global.RoleMenu;

/**
 * Created by Shengwm on 2017/5/17.
 */
public interface UserAuthRoleMenuMapper {

    //获取角色对应的菜单id
    RoleMenu getUserRoleId(Integer roleId) throws Exception;
}
