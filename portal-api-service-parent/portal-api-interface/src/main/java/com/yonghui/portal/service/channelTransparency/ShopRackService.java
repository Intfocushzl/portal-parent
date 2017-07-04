package com.yonghui.portal.service.channelTransparency;

import com.yonghui.portal.model.channelTransparency.ShopRackVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
public interface ShopRackService {

    int deleteByPrimaryKey(Long id);

    int insert(ShopRackVO record);

    int insertSelective(ShopRackVO record);

    ShopRackVO selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ShopRackVO record);

    int updateByPrimaryKey(ShopRackVO record);

    List<ShopRackVO> getShopRackVOSByshopId(String shopid);

    List<ShopRackVO> queryRacks(Map<String,Object> map);

    void insertlist(List<ShopRackVO> data);
}
