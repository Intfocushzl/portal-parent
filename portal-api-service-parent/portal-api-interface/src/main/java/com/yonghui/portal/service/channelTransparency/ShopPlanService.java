package com.yonghui.portal.service.channelTransparency;

import com.yonghui.portal.model.channelTransparency.ShopPlanVO;

/**
 * Created by Shengwm on 2017/7/4.
 */
public interface ShopPlanService {
    int insert(ShopPlanVO record);

    int insertSelective(ShopPlanVO record);

    ShopPlanVO selectByShopid(String shopid);

    int updateByShopid(ShopPlanVO shopPlanVO);
}
