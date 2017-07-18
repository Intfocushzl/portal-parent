package com.yonghui.portal.mapper.global;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.global.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-15 20:03:17
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    Map<String,String> queryMenuIdList(Integer roleId);

    void deleteRoleMenuByRoleId(Integer roleId);

    void saveRoleMenu(Map<String, Object> map);

    void updateRoleMenu(Map<String, Object> map);

    Integer getNextRoleId();

    void deleteUselessRoleMenu();
}
