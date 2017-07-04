package com.yonghui.portal.service.channelTransparency;

import com.yonghui.portal.model.channelTransparency.ShopImgVO;

import java.util.List;

/**
 * Created by Shengwm on 2017/7/4.
 */
public interface ShopImgService {

    int deleteByPrimaryKey(Long id);

    int insert(ShopImgVO record);

    int insertSelective(ShopImgVO record);

    ShopImgVO selectByPrimaryKey(Long id);

    List<ShopImgVO> selectByShopid(String shopid);

    int updateByPrimaryKeySelective(ShopImgVO record);

    int updateByPrimaryKey(ShopImgVO record);

    void insertlist(List<ShopImgVO> data);
}
