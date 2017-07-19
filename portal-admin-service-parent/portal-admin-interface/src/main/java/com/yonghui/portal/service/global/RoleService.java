package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.Role;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/17
 * Description :
 */
public interface RoleService {
    Role queryObject(Integer id);

    List<Role> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(Role role);

    void update(Role role);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    List<Integer> queryMenuIdList(Integer id);

    List<Menu> queryMenuList(Integer roleId);

    int getNextRoleId();
}
