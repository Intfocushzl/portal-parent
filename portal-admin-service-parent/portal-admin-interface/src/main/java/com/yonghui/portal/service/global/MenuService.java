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

    List<Map<String, Object>> getNewAreaMansList();

    List<Map<String, Object>> getNewAreaMansListByUser(String district);

    List<Map<String, Object>> getNewStoreListByDistrict(String district, List<String> list);

    List<Map<String, Object>> getNewStoreListByDistrictNotUser(String district);

    //List<Map<String,Object>> getCJWZStoreUserList();
    List<Map<String, Object>> getLargeAreaList();

    List<Map<String, Object>> getLargeAreaListByUser(String district);

    List<Map<String, Object>> getStoreListByDistrict(String district, List<String> list);

    List<Map<String, Object>> broveStoreLittleIdList(List<Integer> list);

    List<Map<String, Object>> getFifthRotation();//商行

    List<Map<String, Object>> broveStoreLittleIdNotTwoOneOneList(List<Integer> list);

    List<Map<String, Object>> broveStoreLittleIdListByFinance(List<Integer> list);

    List<Map<String, Object>> secondClusterList(String district, String province, String areaName);
}
