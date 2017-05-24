package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.horse.HorseSort;

import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/5/24.
 */
public interface HorseMonthlyAnalysisService {

    public List<Map<String, Object>> totel(String sdate, String sapshopid, String groupid);

    public List<HorseSort> getShopHorseResultAscByList(String  lkpDate, String shopId, String groupId);
}
