package com.yonghui.portal.mapper.schedule;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.schedule.ScheduleJob;

import java.util.Map;

/**
 * 定时任务
 * 
 */
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
