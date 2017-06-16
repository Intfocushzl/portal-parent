package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.sys.KpiCatsalesRealtimeMapper;
import com.yonghui.portal.mapper.global.NoticeMapper;
import com.yonghui.portal.mapper.sys.StHydTotalRealTimeMapper;
import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.service.global.NoticeService;
import com.yonghui.portal.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeMapper noticeMapper;
	@Autowired
	private KpiCatsalesRealtimeMapper kpiCatsalesRealtimeMapper;
	@Autowired
	private StHydTotalRealTimeMapper stHydTotalRealTimeMapper;


	@Override
	public List<Notice> getNewList() {
		return noticeMapper.getNewList();
	}

	@Override
	public Notice showDetail(int id) {
		return noticeMapper.showDetail(id);
	}


	@Override
	public List<Double> realTimeNowSalesTrendLine(Map<String, Object> map) {
		return stHydTotalRealTimeMapper.realTimeNowSalesTrendLine(map);
	}

	@Override
	public List<Double> indexRealTimeSaleLine() {
		return kpiCatsalesRealtimeMapper.indexRealTimeSaleLine();
	}

	@Override
	public List<Double> indexRealTimeLastSaleLine() {
		return kpiCatsalesRealtimeMapper.indexRealTimeLastSaleLine();
	}

}
