package com.yonghui.portal.mapper.channelTransparency;

import com.yonghui.portal.model.channelTransparency.ShopPlanVO;

public interface ShopPlanVOMapper {
    int insert(ShopPlanVO record);
    
    int insertSelective(ShopPlanVO record);
    
    ShopPlanVO selectByShopid(String shopid);
    
    int updateByShopid(ShopPlanVO shopPlanVO);
}