package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.OdsHanaHtsrMapper;
import com.yonghui.portal.model.channelTransparency.OdsHanaHtsr;
import com.yonghui.portal.service.channelTransparency.OdsHanaHtsrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/11.
 */
@Service
public class OdsHanaHtsrServiceImpl implements OdsHanaHtsrService{
    @Resource
    private OdsHanaHtsrMapper odsHanaHtsrMapper;

    @Override
    public List<Map<String, Object>> tlxList() {
        return odsHanaHtsrMapper.tlxList();
    }

    @Override
    public List<Map<String, Object>> purchOrgList() {
        return odsHanaHtsrMapper.purchOrgList();
    }

    @Override
    public List<Map<String, Object>> purGourpList() {
        return odsHanaHtsrMapper.purGourpList();
    }
    @Override
    public List<OdsHanaHtsr> getAllGalleryCost(Map<String, Object> map) {
        return odsHanaHtsrMapper.getAllGalleryCost(map);
    }

    @Override
    public List<OdsHanaHtsr> getAllZopc(String zopc) {
        return odsHanaHtsrMapper.getAllZopc(zopc);
    }
}
