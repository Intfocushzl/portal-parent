package com.yonghui.portal.service.impl.schedule;

import com.yonghui.portal.mapper.schedule.ScheduleJobLogMapper;
import com.yonghui.portal.model.schedule.ScheduleJobLog;
import com.yonghui.portal.service.schedule.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogMapper scheduleJobLogMapper;
	
	public ScheduleJobLog queryObject(Long jobId) {
		return scheduleJobLogMapper.queryObject(jobId);
	}

	public List<ScheduleJobLog> queryList(Map<String, Object> map) {
		return scheduleJobLogMapper.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogMapper.queryTotal(map);
	}

	public void save(ScheduleJobLog log) {
		scheduleJobLogMapper.save(log);
	}

}
