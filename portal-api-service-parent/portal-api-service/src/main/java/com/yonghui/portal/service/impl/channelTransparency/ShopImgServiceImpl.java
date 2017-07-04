package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.ShopImgVOMapper;
import com.yonghui.portal.model.channelTransparency.ShopImgVO;
import com.yonghui.portal.service.channelTransparency.ShopImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class ShopImgServiceImpl implements ShopImgService{

    @Resource
    private ShopImgVOMapper shopImgVOMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return shopImgVOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShopImgVO record) {
        return shopImgVOMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopImgVO record) {
        return shopImgVOMapper.insertSelective(record);
    }

    @Override
    public ShopImgVO selectByPrimaryKey(Long id) {
        return shopImgVOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShopImgVO> selectByShopid(String shopid) {
        return shopImgVOMapper.selectByShopid(shopid);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopImgVO record) {
        return shopImgVOMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopImgVO record) {
        return shopImgVOMapper.updateByPrimaryKey(record);
    }

    @Override
    public void insertlist(List<ShopImgVO> data) {
        shopImgVOMapper.insertlist(data);
    }
}
