package com.yonghui.portal.service.report;


import com.yonghui.portal.model.report.ReportMeasureRelation;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-22 16:17:07
 */
public interface ReportMeasureRelationService {

    ReportMeasureRelation queryObject(Long id);

    List<ReportMeasureRelation> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ReportMeasureRelation reportMeasureRelation);

    void update(ReportMeasureRelation reportMeasureRelation);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
