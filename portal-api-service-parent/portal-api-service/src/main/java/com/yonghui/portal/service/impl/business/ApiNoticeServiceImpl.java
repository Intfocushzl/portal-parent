package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeLogMapper;
import com.yonghui.portal.mapper.businessman.BusinessmanNoticeMapper;
import com.yonghui.portal.service.business.ApiNoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/8.
 */
public class ApiNoticeServiceImpl implements ApiNoticeService {

    @Autowired
    private BusinessmanNoticeMapper businessmanNoticeMapper;
    @Autowired
    private BusinessmanNoticeLogMapper businessmanNoticeLogMapper;

    @Override
    public List<Map<String, Object>> noticeList(Map<String, Object> params) {
        return businessmanNoticeMapper.noticeList(params);
    }

    @Override
    public List<Map<String, Object>> getNoticeById(Integer id) {
        return null;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanNoticeMapper.queryTotal();
    }
}
