package com.yonghui.portal.service;

import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.util.Page;

import java.util.List;
import java.util.Map;

public interface NoticeService {

	Page<Notice> noticeList(Map<String, Object> map);

	void delete(int id);

	void delByIdHide(int id);

	int addByIdHide(int id);
	
	Long addNotice(Notice notice);

	List<Notice> getNewList();
	
	Notice showDetail(int id);

	void update(Map<String, Object> sqlPram);

	//StHydTotalRealTimeMapper
	List<Double> realTimeNowSalesTrendLine(Map<String, Object> map);
	//KpiCatsalesRealtimeMapper
	List<Double> indexRealTimeSaleLine();// 今天
	List<Double> indexRealTimeLastSaleLine();// 上周同天
}
