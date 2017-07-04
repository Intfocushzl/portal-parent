package com.yonghui.portal.mapper.channelTransparency;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yonghui.portal.model.channelTransparency.ShopRackVO;

public interface ShopRackVOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ShopRackVO record);

    int insertSelective(ShopRackVO record);

    ShopRackVO selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ShopRackVO record);

    int updateByPrimaryKey(ShopRackVO record);

	List<ShopRackVO> getShopRackVOSByshopId(@Param(value = "shopid") String shopid);

	void insertlist(List<ShopRackVO> data);

	List<ShopRackVO> queryRacks(Map<String, Object> map);
}