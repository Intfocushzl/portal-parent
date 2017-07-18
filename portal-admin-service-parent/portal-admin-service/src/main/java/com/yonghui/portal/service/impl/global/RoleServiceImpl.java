package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.MenuMapper;
import com.yonghui.portal.mapper.global.RoleMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.Role;
import com.yonghui.portal.service.global.RoleService;
import com.yonghui.portal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Role queryObject(Integer id) {
        return roleMapper.queryObject(id);
    }

    @Override
    public List<Role> queryList(Map<String, Object> map) {
        return roleMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return roleMapper.queryTotal(map);
    }

    @Override
    public void save(Role role) {
        roleMapper.save(role);
        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        saveRoleMenu(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        updateRoleMenu(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
        roleMapper.deleteRoleMenuByRoleId(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        for (Integer id : ids) {
            roleMapper.deleteRoleMenuByRoleId(id);
        }
        roleMapper.deleteBatch(ids);
    }

    @Override
    public List<Integer> queryMenuIdList(Integer id) {
        List<Integer> list = new ArrayList<>();
        Map<String, String> roleMenu = roleMapper.queryMenuIdList(id);
        deleteUselessRoleMenu();
        if (roleMenu == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleId", id);
            map.put("menuId", "");
            roleMapper.saveRoleMenu(map);
        } else {
            System.out.printf("" + roleMenu.get("menuId"));
            String menuIds = roleMenu.get("menuId");
            if (StringUtils.areNotEmpty(menuIds)) {
                String[] strs = menuIds.split(",");
                for (String str : strs) {
                    if (StringUtils.areNotEmpty(str)) {
                        Integer i = Integer.parseInt(str);
                        list.add(i);
                    }
                }
            }
        }
        return list;
    }

    //根据角色查对应权限菜单
    @Override
    public List<Menu> queryMenuList(Integer roleId) {
        return menuMapper.listRoleMenuByRoleId(roleId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(Role role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (role.getCreateUserId() == null || role.getCreateUserId() == 1) {
            return;
        }

       /* //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.queryAllForFrontMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }*/
    }

    private void saveRoleMenu(Integer id, List<Integer> menuIdList) {
        if (menuIdList.size() == 0) {
            return;
        }
        System.out.println(id);
        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<String, Object>();

        String menuId = "";
        for (int i = 0; i < menuIdList.size(); i++) {
            menuId += menuIdList.get(i) + "";
            if (i < menuIdList.size() - 1) {
                menuId += ",";
            }
        }
        map.put("roleId", id);
        map.put("menuId", menuId);
        roleMapper.saveRoleMenu(map);
    }

    private void updateRoleMenu(Integer id, List<Integer> menuIdList) {
        if (menuIdList.size() == 0) {
            return;
        }
        String menuId = "";
        for (int i = 0; i < menuIdList.size(); i++) {
            menuId += menuIdList.get(i) + "";
            if (i < menuIdList.size() - 1) {
                menuId += ",";
            }
        }
        //更新角色与菜单关系
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", id);
        map.put("menuId", menuId);
        roleMapper.updateRoleMenu(map);
    }

    void deleteUselessRoleMenu() {
        roleMapper.deleteUselessRoleMenu();
    }
}
