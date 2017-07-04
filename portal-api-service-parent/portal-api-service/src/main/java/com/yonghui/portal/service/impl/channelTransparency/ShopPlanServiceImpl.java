package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.ShopPlanVOMapper;
import com.yonghui.portal.model.channelTransparency.ShopPlanVO;
import com.yonghui.portal.service.channelTransparency.ShopPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class ShopPlanServiceImpl implements ShopPlanService{

    @Resource
    private ShopPlanVOMapper shopPlanVOMapper;

    @Override
    public int insert(ShopPlanVO record) {
        return shopPlanVOMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopPlanVO record) {
        return shopPlanVOMapper.insertSelective(record);
    }

    @Override
    public ShopPlanVO selectByShopid(String shopid) {
        return shopPlanVOMapper.selectByShopid(shopid);
    }

    @Override
    public int updateByShopid(ShopPlanVO shopPlanVO) {
        return shopPlanVOMapper.updateByShopid(shopPlanVO);
    }
}
