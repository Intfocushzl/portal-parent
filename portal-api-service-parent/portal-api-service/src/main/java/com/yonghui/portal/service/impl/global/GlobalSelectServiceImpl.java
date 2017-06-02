package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.MenuMapper;
import com.yonghui.portal.service.global.GlobalSelectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/2.
 */
public class GlobalSelectServiceImpl implements GlobalSelectService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> getNewAreaMansList() {
        return menuMapper.getNewAreaMansList();
    }

    @Override
    public List<Map<String, Object>> getNewAreaMansListByUser(String district) {
        return menuMapper.getNewAreaMansListByUser(district);
    }

    @Override
    public List<Map<String, Object>> getNewStoreListByDistrict(String district, List<String> list) {
        return menuMapper.getNewStoreListByDistrict(district, list);
    }

    @Override
    public List<Map<String, Object>> getNewStoreListByDistrictNotUser(String district) {
        return menuMapper.getNewStoreListByDistrictNotUser(district);
    }

    @Override
    public List<Map<String, Object>> getLargeAreaListByUser(String district) {
        return menuMapper.getLargeAreaListByUser(district);
    }

    /*@Override
    public List<Map<String, Object>> getCJWZStoreUserList() {
        return menuMapper.getCJWZStoreUserList();
    }*/

    @Override
    public List<Map<String, Object>> getLargeAreaList() {
        return menuMapper.getLargeAreaList();
    }

    @Override
    public List<Map<String, Object>> getStoreListByDistrict(String district, List<String> list) {
        return menuMapper.getStoreListByDistrict(district, list);
    }

    @Override
    public List<Map<String, Object>> broveStoreLittleIdList(List<Integer> list) {
        return menuMapper.broveStoreLittleIdList(list);
    }

    @Override
    public List<Map<String, Object>> getFifthRotation() {
        return menuMapper.getFifthRotation();
    }

    @Override
    public List<Map<String, Object>> broveStoreLittleIdNotTwoOneOneList(List<Integer> list) {
        return menuMapper.broveStoreLittleIdNotTwoOneOneList(list);
    }

    @Override
    public List<Map<String, Object>> broveStoreLittleIdListByFinance(List<Integer> list) {
        return menuMapper.broveStoreLittleIdListByFinance(list);
    }

    @Override
    public List<Map<String, Object>> secondClusterList(String district, String province, String areaName) {
        return menuMapper.secondClusterList(district, province, areaName);
    }
}
