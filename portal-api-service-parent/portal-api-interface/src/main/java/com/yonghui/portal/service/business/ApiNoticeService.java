package com.yonghui.portal.service.business;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/8.
 */
public interface ApiNoticeService {

    //公告列表
    List<Map<String,Object>> noticeList(Map<String, Object> params);
    //单个公告信息
    Map<String,Object> notice(Map<String,Object> params);

    int queryTotal(Map<String, Object> map);



}
