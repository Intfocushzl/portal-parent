package com.yonghui.portal.mapper.sales;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/11
 * Description :
 */
public interface SalesAnalysisMapper {

    List<Map<String, Object>> shopSalesDailyData(Map<String, Object> map);


    List<Map<String, Object>> areaSalesDailyData(Map<String, Object> map);

    List<Map<String,Object>> shopSalesMonthlyData(Map<String, Object> map);

    List<Map<String,Object>> areaSalesMonthlyData(Map<String, Object> map);
}
