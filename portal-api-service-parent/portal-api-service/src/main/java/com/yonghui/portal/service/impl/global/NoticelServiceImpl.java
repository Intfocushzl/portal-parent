package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.sys.KpiCatsalesRealtimeMapper;
import com.yonghui.portal.mapper.global.NoticeMapper;
import com.yonghui.portal.mapper.sys.StHydTotalRealTimeMapper;
import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.service.NoticeService;
import com.yonghui.portal.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class NoticelServiceImpl implements NoticeService {

	@Autowired
	NoticeMapper noticeMapper;
	@Autowired
	private KpiCatsalesRealtimeMapper kpiCatsalesRealtimeMapper;
	@Autowired
	private StHydTotalRealTimeMapper stHydTotalRealTimeMapper;

	@Override
	public Page<Notice> noticeList(Map<String, Object> map) {
		Page<Notice> pa = new Page<Notice>();
		List<Notice> rows = noticeMapper.list(map);
		long total = noticeMapper.total(map);
		pa.setRows(rows);
		pa.setTotal(total);
		return pa;
	}

	@Override
	public void delete(int id) {
		noticeMapper.delete(id);
	}

	@Override
	public void delByIdHide(int id) {
		noticeMapper.delByIdHide(id);
	}

	@Override
	public int addByIdHide(int id) {
		return noticeMapper.addByIdHide(id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long addNotice(Notice notice) {
		return  noticeMapper.add(notice);
	}

	@Override
	public List<Notice> getNewList() {
		return noticeMapper.getNewList();
	}

	@Override
	public Notice showDetail(int id) {
		return noticeMapper.showDetail(id);
	}

	@Override
	public void update(Map<String, Object> sqlPram) {
		noticeMapper.update(sqlPram);
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
