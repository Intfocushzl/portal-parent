package com.yonghui.portal.service.business;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/8.
 */
public interface ApiNoticeService {

    //公告列表
    public List<Map<String,Object>> noticeList(Map<String, Object> params);
    //单个公告信息
    public List<Map<String,Object>> getNoticeById(Integer id);

    int queryTotal(Map<String, Object> map);


}
