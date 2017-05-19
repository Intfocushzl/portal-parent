package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.sys.UserAuthMenuMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.service.sys.UserAuthMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**用户菜单
 * Created by Shengwm on 2017/5/17.
 */
@Service("userAuthMenuService")
public class UserAuthMenuServiceImpl implements UserAuthMenuService {
    @Resource
    private UserAuthMenuMapper userAuthMenuMapper;
    //所有菜单
    public List<Menu> listMenu() {
        List<Menu> list = new ArrayList<>();
        try {
            list = userAuthMenuMapper.listMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //角色菜单
    public List<Menu> listRoleMenu(List<Integer> list) {
        List<Menu> listMenu = new ArrayList<>();
        try {
            listMenu = userAuthMenuMapper.listRoleMenu(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }
}
