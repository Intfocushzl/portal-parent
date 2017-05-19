package com.yonghui.portal.mapper.horse;

import com.yonghui.portal.model.horse.HorseStoreWarning;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface HorseStoreWarningMapper {


    List<HorseStoreWarning> storeLight(Map<String, Object> map);// 门店亮灯

    int shopOffLightBySaleFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByZZFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByJxcFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByProFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByQtyFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByKdFlag(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);


    int shopOffLightBySaleFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByZZFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByJxcFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByProFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByQtyFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    int shopOffLightByKdFlagQy(@Param(value = "groupId") int groupId, @Param(value = "shopId") String shopId, @Param(value = "time") String time);

    List<HorseStoreWarning> areaLight(Map<String, Object> map);//    区域亮灯
}
