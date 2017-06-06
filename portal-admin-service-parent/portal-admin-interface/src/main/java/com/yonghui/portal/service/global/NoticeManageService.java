package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.Notice;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/06/05
 * Description :
 */
public interface NoticeManageService {

    Notice queryObject(Integer id);

    List<Notice> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(Notice notice);

    void update(Notice notice);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
