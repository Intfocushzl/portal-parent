package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.ReportMeasureRelationMapper;
import com.yonghui.portal.model.report.ReportMeasureRelation;
import com.yonghui.portal.service.report.ReportMeasureRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("reportMeasureRelationService")
public class ReportMeasureRelationServiceImpl implements ReportMeasureRelationService {
    @Autowired
    private ReportMeasureRelationMapper reportMeasureRelationMapper;

    @Override
    public ReportMeasureRelation queryObject(Long id) {
        return reportMeasureRelationMapper.queryObject(id);
    }

    @Override
    public List<ReportMeasureRelation> queryList(Map<String, Object> map) {
        return reportMeasureRelationMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return reportMeasureRelationMapper.queryTotal(map);
    }

    @Override
    public void save(ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationMapper.save(reportMeasureRelation);
    }

    @Override
    public void update(ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationMapper.update(reportMeasureRelation);
    }

    @Override
    public void delete(Long id) {
        reportMeasureRelationMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        reportMeasureRelationMapper.deleteBatch(ids);
    }

}
