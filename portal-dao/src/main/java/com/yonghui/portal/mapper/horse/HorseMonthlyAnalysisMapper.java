package com.yonghui.portal.mapper.horse;

import com.yonghui.portal.model.horse.HorseSort;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HorseMonthlyAnalysisMapper {

	// 门店赛马解读 汇总
	public List<Map<String,Object>> totel(@Param("sdate") String sdate, @Param("sapshopid") String sapshopid,
						   @Param("groupid") String groupid);

	public List<HorseSort> getShopHorseResultAscByList(@Param(value="lkpDate")String  lkpDate, @Param(value="shopId")String shopId, @Param(value="groupId")String groupId);
}
