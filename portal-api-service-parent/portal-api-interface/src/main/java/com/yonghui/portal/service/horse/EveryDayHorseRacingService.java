package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.horse.EveryDayHorseRacing;
import com.yonghui.portal.model.horse.EveryDayHorseRacingNew;

import java.util.List;
import java.util.Map;

public interface EveryDayHorseRacingService {

	List<EveryDayHorseRacing> sameGroupRanking(Map<String, Object> map);// 同组商行排名

	List<EveryDayHorseRacing> horseAnalysis(Map<String, Object> map);// 赛马结果分析
	
	
	public List<EveryDayHorseRacingNew> groupHorseTotal(Map<String, Object> map);
	
	public List<EveryDayHorseRacingNew> shopHorse(Map<String, Object> map);

}
