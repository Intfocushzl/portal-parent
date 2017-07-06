package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleGradeMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleGrade;
import com.yonghui.portal.service.businessman.BusinessmanActicleGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("businessmanActicleGradeService")
public class BusinessmanActicleGradeServiceImpl implements BusinessmanActicleGradeService {
    @Autowired
    private BusinessmanActicleGradeMapper businessmanActicleGradeMapper;

    @Override
    public BusinessmanActicleGrade queryObject(Long id) {
        return businessmanActicleGradeMapper.queryObject(id);
    }

    @Override
    public List<BusinessmanActicleGrade> queryList(Map<String, Object> map) {
        return businessmanActicleGradeMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanActicleGradeMapper.queryTotal(map);
    }

    @Override
    public void save(BusinessmanActicleGrade businessmanActicleGrade) {
        businessmanActicleGradeMapper.save(businessmanActicleGrade);
    }

    @Override
    public void update(BusinessmanActicleGrade businessmanActicleGrade) {
        businessmanActicleGradeMapper.update(businessmanActicleGrade);
    }

    @Override
    public void delete(Long id) {
        businessmanActicleGradeMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        businessmanActicleGradeMapper.deleteBatch(ids);
    }

    @Override
    public List<BusinessmanActicleGrade> getListByActicleId(Integer id) {
        return businessmanActicleGradeMapper.getListByActicleId(id);
    }

}
