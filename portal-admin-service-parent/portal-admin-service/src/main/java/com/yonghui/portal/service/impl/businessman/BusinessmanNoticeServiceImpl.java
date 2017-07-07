package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeMapper;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.service.businessman.BusinessmanNoticeService;
import com.yonghui.portal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanNoticeService")
public class BusinessmanNoticeServiceImpl implements BusinessmanNoticeService {
    @Autowired
    private BusinessmanNoticeMapper businessmanNoticeMapper;

    @Override
    public BusinessmanNotice queryObject(Long id) {
        return businessmanNoticeMapper.queryObject(id);
    }

    @Override
    public List<BusinessmanNotice> queryList(Map<String, Object> map) {
        return businessmanNoticeMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanNoticeMapper.queryTotal(map);
    }

    @Override
    public void save(BusinessmanNotice businessmanNotice) {
        if (businessmanNotice.getStatus() == 2) {   // 发布 2发布操作，1保存新草稿操作
            businessmanNotice.setContent(businessmanNotice.getContentManuscript());
        }
        if (StringUtils.isEmpty(businessmanNotice.getContent())) {
            businessmanNotice.setDisabled(1L);     //  当用户点击保存时如果 正文为空则禁用该文章
        }
        businessmanNoticeMapper.save(businessmanNotice);
    }

    @Override
    public void update(BusinessmanNotice businessmanNotice) {
        if (businessmanNotice.getStatus() == 2) {   // 发布 2发布操作，1保存新草稿操作
            businessmanNotice.setContent(businessmanNotice.getContentManuscript());
        }
        if (StringUtils.isEmpty(businessmanNotice.getContent())) {
            businessmanNotice.setDisabled(1L);     //  当用户点击保存时如果 正文为空则禁用该文章
        }
        businessmanNoticeMapper.update(businessmanNotice);
    }

    @Override
    public void delete(Long id) {
        businessmanNoticeMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        businessmanNoticeMapper.deleteBatch(ids);
    }

}
