package com.yonghui.portal.service.impl.horse;


import com.yonghui.portal.mapper.horse.EveryDayHorseRacingMapper;
import com.yonghui.portal.model.horse.EveryDayHorseRacing;
import com.yonghui.portal.model.horse.EveryDayHorseRacingNew;
import com.yonghui.portal.service.horse.EveryDayHorseRacingService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
 * Created by liuwei on 2017/5/6.
 */
public class EveryDayHorseRacingServiceImpl implements EveryDayHorseRacingService {

    @Resource
    private EveryDayHorseRacingMapper everyDayHorseRacingMapper;

    public List<EveryDayHorseRacing> sameGroupRanking(Map<String, Object> map) {
        return everyDayHorseRacingMapper.sameGroupRanking(map);
    }

    public List<EveryDayHorseRacing> horseAnalysis(Map<String, Object> map) {
        return everyDayHorseRacingMapper.horseAnalysis(map);
    }

    public List<EveryDayHorseRacingNew> groupHorseTotal(Map<String, Object> map) {
        return everyDayHorseRacingMapper.groupHorseTotal(map);
    }

    public List<EveryDayHorseRacingNew> shopHorse(Map<String, Object> map) {
        return everyDayHorseRacingMapper.shopHorse(map);
    }

    //商行赛马指标明细下载
    public List<Map<String, Object>> groupHorse(){
       return everyDayHorseRacingMapper.groupHorse();
    }
}
