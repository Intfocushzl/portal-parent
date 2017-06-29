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
        if (businessmanActicle.getStatus() == 1) {//保存草稿  状态1 草稿字段不为空  正文为空 禁用
            if (StringUtils.isEmpty(businessmanActicle.getContent())) {
                businessmanActicle.setContent(businessmanActicle.getContentManuscript());
            }
            businessmanActicle.setDisabled(1L);
        } else if (businessmanActicle.getStatus() == 2) {//发布  状态2     启用
            businessmanActicle.setContent(businessmanActicle.getContentManuscript());
            businessmanActicle.setDisabled(0L);
        }
        businessmanActicleMapper.save(businessmanActicle);
    }

    @Override
    public void update(BusinessmanActicle businessmanActicle) {
        if (businessmanActicle.getStatus() == 1) {//发布完修改存草稿  都不为空 草稿字段新 内容老  状态2   启用
            businessmanActicle.setStatus(2L);
            businessmanActicle.setDisabled(1L);
        } else if (businessmanActicle.getStatus() == 2) {//发不完修改发布			一样最新		2		启用
            businessmanActicle.setDisabled(0L);
            businessmanActicle.setContent(businessmanActicle.getContentManuscript());
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
