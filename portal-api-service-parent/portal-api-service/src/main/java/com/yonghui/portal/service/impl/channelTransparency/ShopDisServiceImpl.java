package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.ShopDisVOMapper;
import com.yonghui.portal.model.channelTransparency.ShopDisVO;
import com.yonghui.portal.service.channelTransparency.ShopDisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class ShopDisServiceImpl implements ShopDisService {

   @Resource
   private ShopDisVOMapper shopDisVOMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return shopDisVOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShopDisVO record) {
        return shopDisVOMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopDisVO record) {
        return shopDisVOMapper.insertSelective(record);
    }

    @Override
    public ShopDisVO selectByPrimaryKey(Long id) {
        return shopDisVOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopDisVO record) {
        return shopDisVOMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopDisVO record) {
        return shopDisVOMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopDisVO> selectByShopidAndGroupidAndShopid(String dateno, String groupid, String shopid) {
        return shopDisVOMapper.selectByShopidAndGroupidAndShopid(dateno,groupid,shopid);
    }

    @Override
    public List<Map<String, Object>> listGroup() {
        return shopDisVOMapper.listGroup();
    }

    @Override
    public List<Map<String, Object>> listDateNo() {
        return shopDisVOMapper.listDateNo();
    }
}
