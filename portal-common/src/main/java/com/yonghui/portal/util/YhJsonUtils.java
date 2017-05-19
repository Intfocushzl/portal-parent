package com.yonghui.portal.util;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class YhJsonUtils {
	Logger log = Logger.getLogger(this.getClass());
	public JSONObject toEasyUiJSONString(Object o,int total){
		JSONObject json=new JSONObject();
		json.put("rows", o);
		json.put("total", total);
		return json;
	}
	public JSONObject toEasyUiJSONString(Object columns , Object rows,int total){
		JSONObject json=new JSONObject();
		json.put("columns", columns);
		json.put("rows", rows);
		json.put("total", total);
		return json;
	}
}
