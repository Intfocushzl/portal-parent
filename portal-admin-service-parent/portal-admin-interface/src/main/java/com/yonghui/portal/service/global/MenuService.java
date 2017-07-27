package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/8.
 */
public interface MenuService {

    //获取角色对应的菜单id
    RoleMenu getUserRoleId(Integer roleId) throws Exception;

    List<Menu> listMenu() throws Exception;

    List<Menu> listRoleMenu(List<Integer> list) throws Exception;

    Menu queryObject(Integer id);

    List<Menu> queryList(Map<String, Object> map);

    List<Menu> queryUserList(Long userId);

    int queryTotal(Map<String, Object> map);

    void save(Menu menu);

    void update(Menu menu);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    List<Map<String,Object>> queryLargeAreaList();

    List<Map<String,Object>> queryAreamsList(String district);

    List<Map<String,Object>> queryFirmsList();

    List<Map<String,Object>> queryShopsList(Map<String, Object> map);

    List<Menu> queryChildrenList(Integer pid);

    List<Menu> queryMenuSort(Integer id);
}

