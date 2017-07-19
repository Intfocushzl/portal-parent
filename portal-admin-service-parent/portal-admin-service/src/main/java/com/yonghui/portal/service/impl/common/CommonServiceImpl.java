package com.yonghui.portal.service.impl.common;

import com.yonghui.portal.mapper.global.CommonMapper;
import com.yonghui.portal.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/07/18
 * Description :
 */
@Service
public class CommonServiceImpl implements CommonService{

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<Map<String, Object>> getDataList(Map<String, Object> params) throws Exception {
        return commonMapper.getDataList(params);
    }
}
