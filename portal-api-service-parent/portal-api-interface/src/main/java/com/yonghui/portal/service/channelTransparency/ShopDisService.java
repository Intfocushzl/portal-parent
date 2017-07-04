package com.yonghui.portal.service.channelTransparency;

import com.yonghui.portal.model.channelTransparency.ShopDisVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
public interface ShopDisService {

    int deleteByPrimaryKey(Long id);

    int insert(ShopDisVO record);

    int insertSelective(ShopDisVO record);

    ShopDisVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopDisVO record);

    int updateByPrimaryKey(ShopDisVO record);

    List<ShopDisVO> selectByShopidAndGroupidAndShopid( String dateno, String groupid, String shopid);

    List<Map<String,Object>> listGroup();

    List<Map<String,Object>> listDateNo();
}
