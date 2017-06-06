package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.NoticeMapper;
import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.service.global.NoticeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/06/05
 * Description :
 */
@Service
public class NoticeManageServiceImpl implements NoticeManageService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Notice queryObject(Integer id){
        return noticeMapper.queryObject(id);
    }

    @Override
    public List<Notice> queryList(Map<String, Object> map){
        return noticeMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return noticeMapper.queryTotal(map);
    }

    @Override
    public void save(Notice notice){
        noticeMapper.save(notice);
    }

    @Override
    public void update(Notice notice){
        noticeMapper.update(notice);
    }

    @Override
    public void delete(Integer id){
        noticeMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids){
        noticeMapper.deleteBatch(ids);
    }

}
