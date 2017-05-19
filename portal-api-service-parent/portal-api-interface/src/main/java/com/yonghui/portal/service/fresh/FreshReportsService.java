package com.yonghui.portal.service.fresh;

import java.util.List;
import java.util.Map;

public interface FreshReportsService {

	public List<Map<String,Object>> shellSurveyReports(Map<String, Object> map);
	
	public List<Map<String,Object>> shellSurvey(Map<String, Object> map);
	
	public List<Map<String,Object>> lossSurveyList(Map<String, Object> map);

	public  List<Map<String,Object>> lossSurvey(Map<String, Object> map);
	
	public List<Map<String,Object>> minusProfitSurvey(Map<String, Object> map);
	

}
