package com.yonghui.portal.mapper.fresh;

import java.util.List;
import java.util.Map;

public interface FreshReportsMapper {

	public List<Map<String,Object>> shellSurveyReports(Map<String, Object> map);
	
	public List<Map<String,Object>> shellSurvey(Map<String, Object> map);
	
	public List<Map<String,Object>> lossSurveyList(Map<String, Object> map);
	
	public List<Map<String,Object>> minusProfitSurvey(Map<String, Object> map);
	
}
