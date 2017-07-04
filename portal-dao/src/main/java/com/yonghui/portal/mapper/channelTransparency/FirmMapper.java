package com.yonghui.portal.mapper.channelTransparency;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yonghui.portal.model.channelTransparency.Shop;

public interface  FirmMapper {
	
	List<Map<String,Object>> broveStoreLittleIdList();
	
	List<Map<String,Object>> getLargeAreaList();
	List<Map<String,Object>> getLargeAreaListNotAll();
    List<Shop>  secondClusterList(@Param(value = "district") String district, @Param(value = "province") String province, @Param(value = "areaName") String areaName, @Param(value = "list") List<String> list)throws Exception ;
    Map<String, Object> getGroupByGroupId(@Param(value = "groupid") String groupid);
    List<Map<String,Object>> salesTargetBroveStoreLittleIdList();
	List<Map<String,Object>> broveByFirmList();
	List<Map<String,Object>> noAllBroveByFirmList();
	List<Map<String,Object>> bravoAreaMansByList(@Param(value = "areaId") String areaId);
	List<Map<String,Object>> broveFirmByClassList(@Param(value = "groupId") Integer groupId);
}
