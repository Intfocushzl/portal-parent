package com.yonghui.portal.mapper.global;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;

import java.util.List;
import java.util.Map;


public interface MenuMapper extends BaseMapper<Menu>{

    //获取角色对应的菜单id
    RoleMenu getUserRoleId(Integer roleId) throws Exception ;

    List<Menu> listMenu() throws Exception ;

    List<Menu> listRoleMenu(List<Integer> list) throws Exception ;

    List<Map<String,Object>> getNewAreaMansList() ;
    List<Map<String,Object>> getNewAreaMansListByUser(String district);
    List<Map<String,Object>> getNewStoreListByDistrict(String district,List<String> list);
    List<Map<String,Object>> getNewStoreListByDistrictNotUser(String district);
   // List<Map<String,Object>> getCJWZStoreUserList();
    List<Map<String,Object>> getLargeAreaList();
    List<Map<String,Object>> getLargeAreaListByUser(String district);
    List<Map<String,Object>> getStoreListByDistrict(String district,List<String> list) ;
    List<Map<String,Object>>  broveStoreLittleIdList(List<Integer> list);
    List<Map<String,Object>>  getFifthRotation();//商行
    List<Map<String,Object>>  broveStoreLittleIdNotTwoOneOneList(List<Integer> list);
    List<Map<String,Object>>  broveStoreLittleIdListByFinance(List<Integer> list);
    List<Map<String,Object>>  secondClusterList(String district ,String province,String areaName);

    /**
     * 根据父菜单，查询子菜单
     * @param pid 父菜单ID
     */
    List<Menu> queryListParentId(Integer pid);

    /**
     * 查询用户的权限列表
     */
    List<Menu> queryUserList(Long userId);

    List<Map<String,Object>> getBravoShopList(Map<String, Object> map);

    List<Map<String,Object>> getNewAreaMansListByLargeArea(Map<String, Object> map);

    List<Menu> listRoleMenuByRoleId(Integer roleId);
}



