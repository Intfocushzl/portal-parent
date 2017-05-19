package com.yonghui.portal.mapper.sys;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author handx brova实时数据
 *
 */
public interface KpiCatsalesRealtimeMapper {

	List<Double> brovaRealTimeNowSalesTrendLine(Map<String, Object> map);

	List<Double> indexRealTimeSaleLine();// 今天
	List<Double> indexRealTimeLastSaleLine();// 上周同天
}
