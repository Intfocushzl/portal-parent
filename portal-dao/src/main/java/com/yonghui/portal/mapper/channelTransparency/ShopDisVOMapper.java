package com.yonghui.portal.mapper.channelTransparency;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yonghui.portal.model.channelTransparency.ShopDisVO;

public interface ShopDisVOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopDisVO record);

    int insertSelective(ShopDisVO record);

    ShopDisVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopDisVO record);

    int updateByPrimaryKey(ShopDisVO record);
    
    List<ShopDisVO> selectByShopidAndGroupidAndShopid(@Param(value = "dateno") String dateno, @Param(value = "groupid") String groupid, @Param(value = "shopid") String shopid);
    
    List<Map<String,Object>> listGroup();
    
    List<Map<String,Object>> listDateNo();
}