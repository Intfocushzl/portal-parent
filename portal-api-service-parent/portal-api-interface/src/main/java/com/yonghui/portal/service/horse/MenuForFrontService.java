package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;
import java.util.List;

public interface MenuForFrontService {
    //获取角色对应的菜单id
    RoleMenu getUserRoleId(Integer roleId) throws Exception ;

    List<Menu> listMenu() throws Exception ;

    List<Menu> listRoleMenu(List<Integer> list) throws Exception ;
}



