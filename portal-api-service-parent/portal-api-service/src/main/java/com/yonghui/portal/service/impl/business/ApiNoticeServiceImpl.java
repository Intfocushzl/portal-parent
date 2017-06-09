package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeLogMapper;
import com.yonghui.portal.mapper.businessman.BusinessmanNoticeMapper;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.model.businessman.BusinessmanNoticeLog;
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
    public BusinessmanNotice notice(Map<String,Object> params) {
        BusinessmanNotice bsn = businessmanNoticeMapper.queryObject(params.get("id"));
        BusinessmanNoticeLog bsnl = businessmanNoticeLogMapper.queryIsSee(params);
        if( bsn != null && bsnl==null){
            BusinessmanNoticeLog nlog = new BusinessmanNoticeLog();
            Long id = Long.parseLong(params.get("id").toString());
            Long creater = Long.parseLong(params.get("userId").toString());
            nlog.setNoticeId(id);
            nlog.setCreater(creater);
            businessmanNoticeLogMapper.save(nlog);
        }
        return bsn;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanNoticeMapper.queryTotal();
    }
}
