package com.yonghui.portal.service.schedule;


import com.yonghui.portal.model.schedule.ScheduleJobLog;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * 
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLog queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLog> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void save(ScheduleJobLog log);
	
}
