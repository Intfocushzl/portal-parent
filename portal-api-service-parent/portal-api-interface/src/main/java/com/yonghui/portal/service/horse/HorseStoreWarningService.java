package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.horse.HorseStoreWarning;

import java.util.List;
import java.util.Map;


public interface HorseStoreWarningService {

    List<HorseStoreWarning> storeLight(Map<String, Object> map);// 门店亮灯

    int shopOffLightBySaleFlag(int groupId, String shopId, String time);

    int shopOffLightByZZFlag(int groupId, String shopId, String time);

    int shopOffLightByJxcFlag(int groupId, String shopId, String time);

    int shopOffLightByProFlag(int groupId, String shopId, String time);

    int shopOffLightByQtyFlag(int groupId, String shopId, String time);

    int shopOffLightByKdFlag(int groupId, String shopId, String time);

    int shopOffLightBySaleFlagQy(int groupId, String shopId, String time);

    int shopOffLightByZZFlagQy(int groupId, String shopId, String time);

    int shopOffLightByJxcFlagQy(int groupId, String shopId, String time);

    int shopOffLightByProFlagQy(int groupId, String shopId, String time);

    int shopOffLightByQtyFlagQy(int groupId, String shopId, String time);

    int shopOffLightByKdFlagQy(int groupId, String shopId, String time);

    List<HorseStoreWarning> areaLight(Map<String, Object> map);// 区域亮灯
}
