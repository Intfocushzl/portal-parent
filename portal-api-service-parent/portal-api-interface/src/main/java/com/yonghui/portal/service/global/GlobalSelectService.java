package com.yonghui.portal.service.global;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/2.
 * 公共下拉框查询接口
 */
public interface GlobalSelectService {

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
