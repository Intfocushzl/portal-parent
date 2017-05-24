package com.yonghui.portal.service.impl.horse;

import com.yonghui.portal.mapper.horse.HorseMonthlyAnalysisMapper;
import com.yonghui.portal.service.horse.HorseMonthlyAnalysisService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/5/24.
 */
public class HorseMonthlyAnalysisServiceImpl implements HorseMonthlyAnalysisService {


    @Resource
    private HorseMonthlyAnalysisMapper horseMonthlyAnalysisMapper;

    @Override
    public List<Map<String, Object>> totel(String sdate, String sapshopid, String groupid) {
        return horseMonthlyAnalysisMapper.totel(sdate, sapshopid, groupid);
    }
}
