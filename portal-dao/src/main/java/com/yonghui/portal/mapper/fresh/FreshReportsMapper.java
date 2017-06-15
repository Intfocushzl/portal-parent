package com.yonghui.portal.mapper.fresh;

import java.util.List;
import java.util.Map;

public interface FreshReportsMapper {


	public List<Map<String,Object>> areaExcel(Map<String,Object> map);

	public List<Map<String,Object>> shopExcel(Map<String,Object> map);

	public List<Map<String,Object>> wastageAreaExcel(Map<String,Object> map);

	public List<Map<String,Object>> wastageShopExcel(Map<String,Object> map);

	public List<Map<String,Object>> minusProfitSurvey(Map<String, Object> map);
	
}
