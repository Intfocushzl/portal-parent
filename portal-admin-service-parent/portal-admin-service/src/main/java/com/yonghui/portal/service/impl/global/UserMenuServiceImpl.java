package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.sys.UserAuthMenuMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.service.global.UserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xrr on 2017/5/31.
 */
@Service
public class UserMenuServiceImpl implements UserMenuService {

    @Autowired
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
