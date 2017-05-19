package com.yonghui.portal.controller.horse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.horse.HorseStoreWarning;
import com.yonghui.portal.service.horse.HorseStoreWarningService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.YhJsonUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuwei 天天赛马预警
 */
@RestController
@RequestMapping("/bravo/horsestorewarning")
public class HorseStoreWarningController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private HorseStoreWarningService horseStoreWarningService;

    /**
     * 天天赛马预警按门店汇总
     *
     * @param response
     * @param store
     * @param time
     * @return
     */
    @RequestMapping(value = "storeLight", method = RequestMethod.GET)
    public R storeLight(HttpServletResponse response, String store, String time) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("store", store);
        map.put("time", time);
        try {
            List<HorseStoreWarning> list = horseStoreWarningService.storeLight(map);
            List<HorseStoreWarning> removeList = new ArrayList<HorseStoreWarning>();
            for (HorseStoreWarning s : list) {
                s.getSale_flag().equals(0);
                if (s.getSale_flag().equals(0) && s.getZzts_flag().equals(0) && s.getJxc_flag().equals(0)
                        && s.getJxc_flag().equals(0) && s.getProfitrate_flag().equals(0)
                        && s.getSheetqty_flag().equals(0)) {
                    removeList.add(s);
                }
            }
            list.removeAll(removeList);
            JSONObject data = new YhJsonUtils().toEasyUiJSONString(list, list.size());
            log.info("门店亮灯:" + data.toJSONString());
            return R.success(data);
        } catch (Exception e) {
            log.error("查询天天赛马预警失败异常", e);
            JSONObject obj = new JSONObject();
            obj.put("msg", "查询天天赛马预警失败");
            return R.success(obj);
        }
    }

    // 修改标识
    @RequestMapping(value = "/updateflag", method = RequestMethod.POST)
    public R updateflag(HttpServletRequest request, HttpServletResponse response, Integer groupId, String shopId, String time, String flagType) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (flagType == null || flagType == "") {
                log.info("天天赛马预警修改标识,flagType为空");
                return R.success(0);
            } else {
                if (flagType.equals("sale_flag")) {
                    // 修改销售同比标识，门店
                    int i = horseStoreWarningService.shopOffLightBySaleFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("profitrate_flag")) {
                    // 修改毛利率标识，门店
                    int i = horseStoreWarningService.shopOffLightByProFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("sheetqty_flag")) {
                    // 修改客流标识，门店
                    int i = horseStoreWarningService.shopOffLightByQtyFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("kd_flag")) {
                    // 修改客单标识，门店
                    int i = horseStoreWarningService.shopOffLightByKdFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("zzts_flag")) {
                    // 修改周转天数标识，门店
                    int i = horseStoreWarningService.shopOffLightByZZFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("jxc_flag")) {
                    // 修改进销存标识，门店
                    int i = horseStoreWarningService.shopOffLightByJxcFlag(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("sale_flag1")) {
                    // 修改销售同比标识，区总
                    int i = horseStoreWarningService.shopOffLightBySaleFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("profitrate_flag1")) {
                    // 修改毛利率标识，区总
                    int i = horseStoreWarningService.shopOffLightByProFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("sheetqty_flag1")) {
                    // 修改客流标识，区总
                    int i = horseStoreWarningService.shopOffLightByQtyFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("kd_flag1")) {
                    // 修改客单标识，区总
                    int i = horseStoreWarningService.shopOffLightByKdFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("zzts_flag1")) {
                    // 修改周转标识，区总
                    int i = horseStoreWarningService.shopOffLightByZZFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else if (flagType.equals("jxc_flag1")) {
                    // 修改进销存标识，区总
                    int i = horseStoreWarningService.shopOffLightByJxcFlagQy(groupId, shopId, time);
                    return R.success(i);
                } else {
                    return R.success(0);
                }
            }
        } catch (Exception e) {
            log.error("门店熄灯异常", e);
            return R.success(0);
        }
    }


    /**
     * 天天赛马预警按区域汇总
     *
     * @param response
     * @param time
     * @return
     * @author liuwei
     */
    @RequestMapping(value = "areaLight", method = RequestMethod.GET)
    public R areaLight(HttpServletResponse response, String areaname, String groupId, String time) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("areaname", areaname);
        map.put("groupId", groupId);
        map.put("time", time);
        try {
            List<HorseStoreWarning> list = horseStoreWarningService.areaLight(map);
            List<HorseStoreWarning> removeList = new ArrayList<HorseStoreWarning>();
            for (HorseStoreWarning s : list) {
                if (s.getSale_flag().equals(0) && s.getZzts_flag().equals(0) && s.getJxc_flag().equals(0)
                        && s.getJxc_flag().equals(0) && s.getProfitrate_flag().equals(0)
                        && s.getSheetqty_flag().equals(0)) {
                    removeList.add(s);
                }
            }
            list.removeAll(removeList);
            JSONObject data = new YhJsonUtils().toEasyUiJSONString(list, list.size());
            log.info("区域亮灯:" + data.toJSONString());
            return R.success(data);
        } catch (Exception e) {
            log.error("查询天天赛马预警失败异常", e);
            JSONObject obj = new JSONObject();
            obj.put("msg", "查询天天赛马预警失败");
            return R.success(obj);
        }
    }
}
