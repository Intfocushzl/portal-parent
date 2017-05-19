package com.yonghui.portal.service.impl.horse;

import com.yonghui.portal.mapper.horse.HorseStoreWarningMapper;
import com.yonghui.portal.model.horse.HorseStoreWarning;
import com.yonghui.portal.service.horse.HorseStoreWarningService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/5/6.
 */
public class HorseStoreWarningServiceImpl implements HorseStoreWarningService {

    @Resource
    private HorseStoreWarningMapper horseStoreWarningMapper;

    @Override
    public List<HorseStoreWarning> areaLight(Map<String, Object> map) {
        return horseStoreWarningMapper.areaLight(map);
    }

    @Override
    public List<HorseStoreWarning> storeLight(Map<String, Object> map) {
        return horseStoreWarningMapper.storeLight(map);
    }

    @Override
    public int shopOffLightBySaleFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightBySaleFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByZZFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByZZFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByJxcFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByJxcFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByProFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByProFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByQtyFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByQtyFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByKdFlag(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByKdFlag(groupId, shopId, time);
    }

    @Override
    public int shopOffLightBySaleFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightBySaleFlagQy(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByZZFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByZZFlagQy(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByJxcFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByJxcFlagQy(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByProFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByProFlagQy(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByQtyFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByQtyFlagQy(groupId, shopId, time);
    }

    @Override
    public int shopOffLightByKdFlagQy(int groupId, String shopId, String time) {
        return horseStoreWarningMapper.shopOffLightByKdFlagQy(groupId, shopId, time);
    }
}
