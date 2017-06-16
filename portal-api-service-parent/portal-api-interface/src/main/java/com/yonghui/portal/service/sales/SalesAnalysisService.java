package com.yonghui.portal.service.sales;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/25
 * Description :
 */
public interface SalesAnalysisService {

    /**
     * 图表数据
     * @param map
     * @return
     */
    List<Map<String, Object>> salesAnalysisDailyData(Map<String, Object> map);

    List<Map<String,Object>> salesAnalysisMonthlyData(Map<String, Object> map);
}
