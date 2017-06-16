package com.yonghui.portal.controller.horse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.horse.EveryDayHorseRacing;
import com.yonghui.portal.model.horse.EveryDayHorseRacingNew;
import com.yonghui.portal.service.horse.EveryDayHorseRacingService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.YhJsonUtils;
import com.yonghui.portal.util.horse.groupHorseExport;
import com.yonghui.portal.util.horse.horseDailyRacingExport;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author handx 天天赛马
 */

@RestController
@RequestMapping("/bravo/horsedailyracing")
public class HorseDailyRacingController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private EveryDayHorseRacingService everyDayHorseRacingService;

    @RequestMapping(value = "sameGroupRanking")
    public R sameGroupRanking(HttpServletResponse response, String store, String groupId, String grouptxt) {
        Map<String, Object> map = new HashMap<>();
        map.put("store", store);
        map.put("groupId", groupId);
        List<EveryDayHorseRacing> list = everyDayHorseRacingService.sameGroupRanking(map);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTotal(list.get(i).getSale() + list.get(i).getInventory() + list.get(i).getTurnoverDays());
            list.get(i).setGroupname(grouptxt);
        }
        JSONObject data = new YhJsonUtils().toEasyUiJSONString(list, list.size());
        log.info("同组商行排名:" + data.toJSONString());
        return R.success(data);
    }

    @RequestMapping(value = "horseAnalysis")
    public R horseAnalysis(HttpServletResponse response, String store, String groupId, String grouptxt) {
        Map<String, Object> map = new HashMap<>();
        map.put("store", store);
        map.put("groupId", groupId);
        List<EveryDayHorseRacing> list = everyDayHorseRacingService.horseAnalysis(map);
        JSONObject data = new YhJsonUtils().toEasyUiJSONString(list, list.size());
        log.info("赛马结果分析:" + data.toJSONString());
        return R.success(data);
    }

    /**
     * 商行赛马 新版
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "groupHorseTotal", method = RequestMethod.GET)
    public R groupHorseTotal(HttpServletRequest req, HttpServletResponse response,
                             String sapshopid, String group_id, Map<String, Object> model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("sapshopid", sapshopid);
        map.put("group_id", group_id);
        try {
            List<EveryDayHorseRacingNew> list = everyDayHorseRacingService.groupHorseTotal(map);
            return R.success(list);
        } catch (Exception e) {
            List<EveryDayHorseRacingNew> list = new ArrayList<>();
            log.error("查询异常", e);
            return R.error();
        }
    }

    /**
     * 门店赛马 新版
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "groupHorseDetail", method = RequestMethod.GET)
    public R groupHorseDetail(HttpServletRequest req, HttpServletResponse response,
                              String sapshopid, String group_id, Map<String, Object> model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("sapshopid", sapshopid);
        map.put("group_id", group_id);
        try {
            List<EveryDayHorseRacingNew> list = everyDayHorseRacingService.shopHorse(map);
            return R.success(list);
        } catch (Exception e) {
            List<EveryDayHorseRacingNew> list = new ArrayList<>();
            log.error("查询异常", e);
            return R.error();
        }
    }

    /**
     * @param req
     * @param response
     * @param sapshopid
     * @param group_id
     * @param model
     * @return
     */
    @RequestMapping(value = "groupHorseDetailExcel", method = RequestMethod.GET)
    public void groupHorseDetailExcel(HttpServletRequest req, HttpServletResponse response, String sapshopid,
                                      String group_id, Map<String, Object> model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("sapshopid", sapshopid);
        map.put("group_id", group_id);
        try {
            List<EveryDayHorseRacingNew> list = everyDayHorseRacingService.shopHorse(map);
            horseDailyRacingExport export = new horseDailyRacingExport();
            HSSFWorkbook workbook = export.exportByHydBudget(list);
            String filename = "SM-Result.xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            log.error("导出excel异常", e);
        }
    }


    @RequestMapping(value = "groupHorseExcel", method = RequestMethod.GET)
    public void groupHorseExcel(HttpServletRequest req, HttpServletResponse response , String sdate) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");

        try {
            List<Map<String, Object>> list = everyDayHorseRacingService.groupHorse(sdate);
            groupHorseExport export = new groupHorseExport();
            HSSFWorkbook workbook = export.exportByHydBudget(list);
            String filename = "groupHorseExcel.xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            log.error("导出excel异常", e);
        }
    }

}
