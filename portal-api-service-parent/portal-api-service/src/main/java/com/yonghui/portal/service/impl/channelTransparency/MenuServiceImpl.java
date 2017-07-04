package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.ChanelMenuMapper;
import com.yonghui.portal.service.channelTransparency.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private ChanelMenuMapper chanelMenuMapper;

    @Override
    public Map<String, Object> getShopByShopId(String shopId) throws Exception {
        return chanelMenuMapper.getShopByShopId(shopId);
    }
}
