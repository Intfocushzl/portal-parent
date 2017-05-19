package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.sys.UserAuthRoleMenuMapper;
import com.yonghui.portal.model.global.RoleMenu;
import com.yonghui.portal.service.sys.UserAuthRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**角色菜单MAPPING
 * Created by Shengwm on 2017/5/17.
 */
@Service("userAuthRoleMenuService")
public class UserAuthRoleMenuServiceImpl implements UserAuthRoleMenuService {
    @Resource
    private UserAuthRoleMenuMapper userAuthRoleMenuMapper;

    @Override
    public RoleMenu getUserRoleId(Integer roleId) {
        RoleMenu roleMenu = new RoleMenu();
        try {
            roleMenu = userAuthRoleMenuMapper.getUserRoleId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleMenu;
    }
}
