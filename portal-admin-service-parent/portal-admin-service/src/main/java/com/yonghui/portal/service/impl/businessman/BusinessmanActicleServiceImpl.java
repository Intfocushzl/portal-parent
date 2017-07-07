package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicle;
import com.yonghui.portal.service.businessman.BusinessmanActicleService;
import com.yonghui.portal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("businessmanActicleService")
public class BusinessmanActicleServiceImpl implements BusinessmanActicleService {
    @Autowired
    private BusinessmanActicleMapper businessmanActicleMapper;

    @Override
    public BusinessmanActicle queryObject(Long id) {
        return businessmanActicleMapper.queryObject(id);
    }

    @Override
    public List<BusinessmanActicle> queryList(Map<String, Object> map) {
        return businessmanActicleMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanActicleMapper.queryTotal(map);
    }

    @Override
    public void save(BusinessmanActicle businessmanActicle) {
        if (businessmanActicle.getStatus() == 2) {   // 发布 2发布操作，1保存新草稿操作
            businessmanActicle.setContent(businessmanActicle.getContentManuscript());
        }
        if (StringUtils.isEmpty(businessmanActicle.getContent())) {
            businessmanActicle.setDisabled(1L);     //  当用户点击保存时如果 正文为空则禁用该文章
        }
        businessmanActicleMapper.save(businessmanActicle);
    }

    @Override
    public void update(BusinessmanActicle businessmanActicle) {
        if (businessmanActicle.getStatus() == 2) {   // 发布 2发布操作，1保存新草稿操作
            businessmanActicle.setContent(businessmanActicle.getContentManuscript());
        }
        if (StringUtils.isEmpty(businessmanActicle.getContent())) {
            businessmanActicle.setDisabled(1L);     //  当用户点击保存时如果 正文为空则禁用该文章
        }
        businessmanActicleMapper.update(businessmanActicle);
    }

    @Override
    public void delete(Long id) {
        businessmanActicleMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        businessmanActicleMapper.deleteBatch(ids);
    }

}
