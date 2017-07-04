package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.ShopRackVOMapper;
import com.yonghui.portal.model.channelTransparency.ShopRackVO;
import com.yonghui.portal.service.channelTransparency.ShopRackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class ShopRackServiceImpl implements ShopRackService {

    @Autowired
    private ShopRackVOMapper shopRackVOMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return shopRackVOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShopRackVO record) {
        return shopRackVOMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopRackVO record) {
        return shopRackVOMapper.insertSelective(record);
    }

    @Override
    public ShopRackVO selectByPrimaryKey(Long id) {
        ShopRackVO shopRackVO = new ShopRackVO();
        return shopRackVOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopRackVO record) {
        return shopRackVOMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopRackVO record) {
        return shopRackVOMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopRackVO> getShopRackVOSByshopId(String shopid) {
        return shopRackVOMapper.getShopRackVOSByshopId(shopid);
    }

    @Override
    public void insertlist(List<ShopRackVO> data) {
        shopRackVOMapper.insertlist(data);

    }

    @Override
    public List<ShopRackVO> queryRacks(Map<String, Object> map) {
        return shopRackVOMapper.queryRacks(map);
    }
}
