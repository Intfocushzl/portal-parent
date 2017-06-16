package com.yonghui.portal.service.impl.fresh;

import com.yonghui.portal.mapper.fresh.FreshReportsMapper;
import com.yonghui.portal.service.fresh.FreshReportsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Service

/**
 *   生鲜商行报表
 */
public class FreshReportsServiceImpl implements FreshReportsService {

	@Autowired
	private FreshReportsMapper freshReportsMapper;

	@Override
	public List<Map<String, Object>> areaExcel(Map<String, Object> map) {
		return freshReportsMapper.areaExcel(map);
	}

	@Override
	public List<Map<String, Object>> shopExcel(Map<String, Object> map) {
		return freshReportsMapper.shopExcel(map);
	}

	@Override
	public List<Map<String, Object>> wastageAreaExcel(Map<String, Object> map) {
		return freshReportsMapper.wastageAreaExcel(map);
	}

	@Override
	public List<Map<String, Object>> wastageShopExcel(Map<String, Object> map) {
		return freshReportsMapper.wastageShopExcel(map);
	}

	//负毛利概况
	@Override
	public List<Map<String, Object>> minusProfitSurvey(Map<String, Object> map) {
		List<Map<String, Object>> list = freshReportsMapper.minusProfitSurvey(map);
		return reportSort(list,map);
	}

	//表格排序
	public  List<Map<String,Object>> reportSort(List<Map<String,Object>> list,Map<String, Object> map){

		List<Map<String, Object>> group = new ArrayList<Map<String, Object>>();
		//第一个表格
		if(list.size()>0 && map.get("holdName") != null && map.get("areaName") == null && map.get("className") == null && map.get("areaMans") == null){
			list.get(0).put("menu", 0);
			list.get(1).put("menu", 1);
			list.get(2).put("menu", 1);
			list.get(3).put("menu", 1);
			list.get(4).put("menu", 2);
			list.get(5).put("menu", 2);
			list.get(6).put("menu", 2);
			list.get(7).put("menu", 3);
			list.get(8).put("menu", 3);
			list.get(9).put("menu", 3);
			list.get(10).put("menu", 3);
			list.get(11).put("menu", 3);
			list.get(12).put("menu", 2);
			list.get(13).put("menu", 2);
			group.add(list.get(0));
			group.add(list.get(1));
			group.add(list.get(4));
			group.add(list.get(2));
			group.add(list.get(5));
			group.add(list.get(6));
			group.add(list.get(3));
			group.add(list.get(12));
			group.add(list.get(7));
			group.add(list.get(8));
			group.add(list.get(13));
			group.add(list.get(9));
			group.add(list.get(10));
			group.add(list.get(11));
			list = group;
		}
		return list;

	}


}



