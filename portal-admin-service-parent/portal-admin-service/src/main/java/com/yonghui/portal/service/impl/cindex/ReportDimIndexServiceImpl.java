package com.yonghui.portal.service.impl.cindex;

import com.yonghui.portal.mapper.cindex.ReportDimIndexMapper;
import com.yonghui.portal.model.cindex.ReportDimIndex;
import com.yonghui.portal.service.cindex.ReportDimIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("reportDimIndexService")
public class ReportDimIndexServiceImpl implements ReportDimIndexService {
    @Autowired
    private ReportDimIndexMapper reportDimIndexMapper;

    @Override
    public ReportDimIndex queryObject(Integer id) {
        return reportDimIndexMapper.queryObject(id);
    }

    @Override
    public List<ReportDimIndex> queryList(Map<String, Object> map) {
        return reportDimIndexMapper.queryList(map);
    }

    @Override
    public List<ReportDimIndex> queryListOpt() {
        return reportDimIndexMapper.queryListOpt();
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return reportDimIndexMapper.queryTotal(map);
    }

    @Override
    public void save(ReportDimIndex reportDimIndex) {
        reportDimIndexMapper.save(reportDimIndex);
    }

    @Override
    public void update(ReportDimIndex reportDimIndex) {
        reportDimIndexMapper.update(reportDimIndex);
    }

    @Override
    public void delete(Integer id) {
        reportDimIndexMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        reportDimIndexMapper.deleteBatch(ids);
    }

}
