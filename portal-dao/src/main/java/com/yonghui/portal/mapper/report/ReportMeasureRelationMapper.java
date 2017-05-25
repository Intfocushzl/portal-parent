package com.yonghui.portal.mapper.report;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.report.ReportMeasureRelation;

import java.util.List;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-22 16:17:07
 */
public interface ReportMeasureRelationMapper extends BaseMapper<ReportMeasureRelation> {

    /**
     * 根据报表编码获取所有列
     * @param reportcode
     * @return
     */
    List<ReportMeasureRelation> queryListByReportCode(String reportcode);

}
