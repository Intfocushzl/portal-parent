package com.yonghui.portal.mapper.sales;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/11
 * Description :
 */
public interface ShopSalesAnalysisDailyMapper {

    // 销售额 本月
    List<Map<String, Object>> shopSaleLine(Map<String, Object> map);


    // 客流 本月
    List<Map<String, Object>> customerSheetLine(Map<String, Object> map);

}
