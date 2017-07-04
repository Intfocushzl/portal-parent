package com.yonghui.portal.mapper.channelTransparency;

import java.util.List;

import com.yonghui.portal.model.channelTransparency.ShopImgVO;

public interface ShopImgVOMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ShopImgVO record);

    int insertSelective(ShopImgVO record);

    ShopImgVO selectByPrimaryKey(Long id);
    
    List<ShopImgVO> selectByShopid(String shopid);
    
    int updateByPrimaryKeySelective(ShopImgVO record);

    int updateByPrimaryKey(ShopImgVO record);
    
    void insertlist(List<ShopImgVO> data);
}