package com.yonghui.portal.mapper.channelTransparency;

import com.yonghui.portal.model.channelTransparency.OdsHanaHtsr;

import java.util.List;
import java.util.Map;



public interface OdsHanaHtsrMapper {

	List<Map<String,Object>> tlxList();
	
	List<Map<String,Object>> purchOrgList(); 
	
	List<Map<String,Object>> purGourpList(); 
	
	List<OdsHanaHtsr> getAllGalleryCost(Map<String, Object> map);
	
	List<OdsHanaHtsr> getAllZopc(String zopc);
	
}
