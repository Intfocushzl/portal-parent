package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.MenuMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;
import com.yonghui.portal.service.global.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/8.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public RoleMenu getUserRoleId(Integer roleId) throws Exception {
        return menuMapper.getUserRoleId(roleId);
    }

    @Override
    public List<Menu> listMenu() throws Exception {
        return menuMapper.listMenu();
    }

    @Override
    public List<Menu> listRoleMenu(List<Integer> list) throws Exception {
        return menuMapper.listRoleMenu(list);
    }

    @Override
    public Menu queryObject(Integer id) {
        return menuMapper.queryObject(id);
    }

    @Override
    public List<Menu> queryList(Map<String, Object> map) {
        return menuMapper.queryList(map);
    }

    @Override
    public List<Menu> queryUserList(Long userId) {
        return menuMapper.queryUserList(userId);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return menuMapper.queryTotal(map);
    }

    @Override
    public void save(Menu menu) {
        menuMapper.save(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public void delete(Integer id) {
        menuMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        menuMapper.deleteBatch(ids);
    }

}
