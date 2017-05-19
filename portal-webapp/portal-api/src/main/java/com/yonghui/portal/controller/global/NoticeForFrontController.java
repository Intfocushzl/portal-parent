package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Symbol;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.series.Line;
import com.yonghui.portal.model.global.Notice;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.NoticeService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author handx -- 公告通知
 *
 */
@RestController
@RequestMapping("/noticeforfront")
public class NoticeForFrontController {

	Logger log = Logger.getLogger(this.getClass());

	@Reference
	NoticeService noticeService;

	String[] dateTime = { "6点", "7点", "8点", "9点", "10点", "11点", "12点", "13点", "14点", "15点", "16点", "17点", "18点", "19点",
			"20点", "21点", "22点", "23点" };


	@RequestMapping(value ="getNewList",produces="application/json;charset=UTF-8")
	@ResponseBody
	public R getNewList(Model model,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Notice> noticeList = noticeService.getNewList();
		try {
			

			for (int i = 0; i < noticeList.size(); i++) {
				String createtime = noticeList.get(i).getCreatetime();
				String month = createtime.substring(5, 7);
				String day = createtime.substring(8, 10);
				noticeList.get(i).setMonth(month);
				noticeList.get(i).setDay(day);
			}
			model.addAttribute("noticeList", noticeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return JSON.toJSONString(noticeList);
		if(noticeList.size()>0){
			return R.success(noticeList);
		}else{
			return R.error();
		}
	}




	@RequestMapping("realTimeLine")
	public R realTimeLine(HttpSession session, HttpServletResponse response, String time) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		User user = (User) session.getAttribute("user");
		
		if (user != null) {
			
			Integer type = user.getType();
			
			if (type == 0) {// 会员店
				Option option = new Option();
				option.title("会员店实时销售");
				option.calculable(true);
				option.tooltip().trigger(Trigger.axis);
				option.xAxis(new CategoryAxis().boundaryGap(false).data(dateTime));
				ValueAxis valueAxis = new ValueAxis();
				option.yAxis(valueAxis);
				Map<String, Object> map = new HashMap<>();
				map.put("date", time);
				Line line = null;
				line = new Line();
				line.setName("今天");
				line.setSymbol(Symbol.none);
				line.setSmooth(true);
				List<Double> nowWeekList = noticeService.realTimeNowSalesTrendLine(map);
				LineData[] lineData = new LineData[] {};
				if (nowWeekList.size() > 0) {
					lineData = new LineData[dateTime.length];
					for (int i = 0; i < nowWeekList.size(); i++) {
						BigDecimal du = new BigDecimal(nowWeekList.get(i)).setScale(0, BigDecimal.ROUND_HALF_UP);
						lineData[i] = new LineData(du, Symbol.emptyCircle, 5);
					}

					int nowWeekSize = nowWeekList.size();
					int dateLlength = dateTime.length;
					if (nowWeekSize < dateLlength) {
						int count = dateLlength - nowWeekSize;
						for (int i = 0; i < count; i++) {
							lineData[nowWeekSize + 1] = new LineData(0, Symbol.emptyCircle, 5);
						}
					}
				} else {
					lineData = new LineData[1];
					lineData[0] = new LineData(0, Symbol.emptyCircle, 5);
				}

				line.data(lineData);
				option.series(line);
				String json = JSONObject.toJSONString(option);
				log.info("折线图数据：" + json);
				/*try {
					StringTools.sWrite(response, json);
				} catch (Exception e) {
					log.error("门店消费周报", e);
				}*/
				if(json!=null){
					return R.success(json);
				}else{
					return R.error();
				}
			} else {// 1brovaif (type == 1)
				return getBravoDataLine(response);
			}

		}else{// 获取不到类型是，默认Bravo
			return getBravoDataLine(response);
		}

	}

	private R getBravoDataLine(HttpServletResponse response) {
		Option option = new Option();

	//	option.title("Bravo实时销售(万)");
		option.calculable(true);
		option.legend("今天", "上周同天");
		option.tooltip().trigger(Trigger.axis);
		option.xAxis(new CategoryAxis().boundaryGap(false).data(dateTime));
		ValueAxis valueAxis = new ValueAxis();
		option.yAxis(valueAxis);

		Line line = null;
		line = new Line();
		line.setName("今天");
		line.setSymbol(Symbol.none);
		line.setSmooth(true);

		List<Double> saleList = noticeService.indexRealTimeSaleLine();

		LineData[] lineData = new LineData[] {};
		if (saleList.size() > 0) {
			lineData = new LineData[saleList.size()];
			for (int i = 0; i < saleList.size(); i++) {

				Double sale = saleList.get(i);
				if (sale != null) {
					BigDecimal du = new BigDecimal(sale / 10000).setScale(1, BigDecimal.ROUND_HALF_UP);
					lineData[i] = new LineData(du, Symbol.emptyCircle, 5);
				} else {
					lineData[i] = new LineData(null, Symbol.emptyCircle, 5);
				}

			}
		} else {
			lineData = new LineData[1];
			lineData[0] = new LineData(0, Symbol.emptyCircle, 5);
		}

		line.data(lineData);
		option.series(line);

		line = new Line();
		line.setName("上周同天");
		line.setSymbol(Symbol.none);
		line.setSmooth(true);

		List<Double> lastSaleList = noticeService.indexRealTimeLastSaleLine();
		if (lastSaleList.size() > 0) {
			lineData = new LineData[lastSaleList.size()];
			for (int i = 0; i < lastSaleList.size(); i++) {

				Double sale = lastSaleList.get(i);
				if (sale != null) {
					BigDecimal du = new BigDecimal(sale / 10000).setScale(1, BigDecimal.ROUND_HALF_UP);
					lineData[i] = new LineData(du, Symbol.emptyCircle, 5);
				} else {
					lineData[i] = new LineData(null, Symbol.emptyCircle, 5);
				}

			}
		} else {
			lineData = new LineData[1];
			lineData[0] = new LineData(0, Symbol.emptyCircle, 5);
		}

		line.data(lineData);
		option.series(line);
		String json = JSONObject.toJSONString(option);
		log.info("brova折线图数据：" + json);
		/*try {
			StringTools.sWrite(response, json);
		} catch (Exception e) {
			log.error("brova消费周报", e);
		}*/
		if(json!=null){
			return R.success(json);
		}else{
			return R.error();
		}
	}

}