package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.ReportModulePageMapper;
import com.yonghui.portal.model.report.ReportModulePage;
import com.yonghui.portal.service.report.ReportModulePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("reportModulePageService")
public class ReportModulePageServiceImpl implements ReportModulePageService {
    @Autowired
    private ReportModulePageMapper reportModulePageMapper;

    @Override
    public ReportModulePage queryObject(Long id) {
        return reportModulePageMapper.queryObject(id);
    }

    @Override
    public List<ReportModulePage> queryList(Map<String, Object> map) {
        return reportModulePageMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return reportModulePageMapper.queryTotal(map);
    }

    @Override
    public void save(ReportModulePage reportModulePage) {
        reportModulePageMapper.save(reportModulePage);
    }

    @Override
    public void update(ReportModulePage reportModulePage) {
        reportModulePageMapper.update(reportModulePage);
    }

    @Override
    public void delete(Long id) {
        reportModulePageMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        reportModulePageMapper.deleteBatch(ids);
    }

}
