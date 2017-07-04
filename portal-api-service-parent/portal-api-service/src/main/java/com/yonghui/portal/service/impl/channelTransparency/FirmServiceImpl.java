package com.yonghui.portal.service.impl.channelTransparency;

import com.yonghui.portal.mapper.channelTransparency.FirmMapper;
import com.yonghui.portal.service.channelTransparency.FirmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/4.
 */
@Service
public class FirmServiceImpl implements FirmService {
    @Resource
    private FirmMapper firmMapper;

    @Override
    public Map<String, Object> getGroupByGroupId(String groupId) {
        return firmMapper.getGroupByGroupId(groupId);
    }
}
