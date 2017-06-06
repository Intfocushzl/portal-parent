package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {

	List<Notice> getNewList();
	
	Notice showDetail(int id);

	//StHydTotalRealTimeMapper
	List<Double> realTimeNowSalesTrendLine(Map<String, Object> map);
	//KpiCatsalesRealtimeMapper
	List<Double> indexRealTimeSaleLine();// 今天
	List<Double> indexRealTimeLastSaleLine();// 上周同天
}
