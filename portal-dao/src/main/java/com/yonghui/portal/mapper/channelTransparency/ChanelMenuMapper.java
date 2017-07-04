package com.yonghui.portal.mapper.channelTransparency;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yonghui.portal.model.channelTransparency.Menu;

public interface ChanelMenuMapper {
	
    int deleteByPrimaryKey(Integer id) throws Exception ;

    int insert(Menu record) throws Exception ;

    int insertSelective(Menu record)throws Exception ;

    Menu selectByPrimaryKey(Integer id) throws Exception ;

    int updateByPrimaryKeySelective(Menu sort) throws Exception ;
    String updateByPrimaryKeySelectivetwo(Menu sorttwo) throws Exception ;

    int updateByPrimaryKey(Menu record) throws Exception ;
    
    List<Menu> listMenu() throws Exception ;
    
    List<Menu> listRoleMenu(List<Integer> list) throws Exception ;
    
    List<Map<String,Object>> storeList()throws Exception ;
    String storeName(String shopcode)throws Exception ;
    List<Map<String,Object>>  storeUserList(List<String> shopcode)throws Exception ;
    Map<String,Object>  getShopByShopCode(String shopcode)throws Exception ;
    List<Map<String,Object>>  broveStoreList(@Param(value = "list") List<String> shopcode, @Param(value = "areaNameList") List<String> areaNameList)throws Exception ;
    List<Map<String,Object>>  broveStoreLittleIdList(@Param(value = "list") List<Integer> list)throws Exception ;
    List<Map<String,Object>>  broveStoreLittleIdNotTwoOneOneList(@Param(value = "list") List<Integer> list)throws Exception ;
   
    List<Map<String,Object>>  broveStoreLittleIdListByFinance(@Param(value = "list") List<Integer> list)throws Exception ;
    List<Map<String,Object>>  secondClusterList(@Param(value = "district") String district, @Param(value = "province") String province, @Param(value = "areaName") String areaName)throws Exception ;
    List<Map<String,Object>>  getStoreListByDistrict(@Param(value = "district") String district, @Param(value = "list") List<String> list)throws Exception ;
    List<Map<String,Object>>  getDepartmentList(@Param(value = "type") int type)throws Exception ;
    List<Map<String,Object>>  getDepartmentPositionList(@Param(value = "type") int type)throws Exception ;
    List<Map<String,Object>>  getFirmList()throws Exception ;//商行
    List<Map<String,Object>>  getFifthRotation()throws Exception ;//商行
    Map<String,Object>  broveStoreById(@Param(value = "littleId") int littleId)throws Exception ;//商行
//    List<Map<String,Object>>  allStoreUserList()throws Exception ;
    
    
//    List<Map<String,Object>> getLargeAreaListByUser() throws Exception ;
    List<Map<String,Object>> getLargeAreaList() throws Exception ;
    List<Map<String,Object>> getLargeAreaListByUser(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getStoreListByDistrictHaveAll(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getStoreListByDistrictIdNotAll(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getStoreListByDistrictNotAll(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getStoreListByDistrictAndStoreIdNotAll(Map<String, Object> map) throws Exception ;
    List<Map<String,Object>> getProvinceList(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getProvinceListByNewAreamans(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getProvinceAllList(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>> getCityList(@Param(value = "province") String province, @Param(value = "district") String district) throws Exception ;
  
    List<Map<String,Object>> getProvinceListByBrova() throws Exception ;
    List<Map<String,Object>> broveStoreListProvinceByCityArea(@Param(value = "areaId") String areaId) throws Exception ;
    List<Map<String,Object>> broveStoreListByAreamans(@Param(value = "areamans") String areamans) throws Exception ;
    
    List<Map<String,Object>> findMenuByName(@Param(value = "name") String name) throws Exception ;
    
    
    List<Map<String,Object>> getBigClass(@Param(value = "id") String id) throws Exception ;
    List<Map<String,Object>> getAllBroveStoreList() throws Exception ;
    List<Map<String,Object>> getHydStoreUserList() throws Exception ;
  
    List<Map<String,Object>> getShopGroupDatas() throws Exception ;
    List<Map<String,Object>> getShopGroupAndLogisticsDatas() throws Exception ;
    
    //====================新大区=================
    
    List<Map<String,Object>> getNewAreaMansList() throws Exception ;
    List<Map<String,Object>> getNewAreaMansListByUser(@Param(value = "district") String district) throws Exception ;
    List<Map<String,Object>>  getNewStoreListByDistrict(@Param(value = "district") String district, @Param(value = "list") List<String> list)throws Exception ;
    List<Map<String,Object>> getNewStoreListByDistrictNotUser(@Param(value = "district") String district) throws Exception ;
    Map<String,Object> getShopByShopId(@Param(value = "shopid") String shopid) throws Exception ;
    List<Map<String,Object>> getCJWZStoreUserList() throws Exception ;
}



