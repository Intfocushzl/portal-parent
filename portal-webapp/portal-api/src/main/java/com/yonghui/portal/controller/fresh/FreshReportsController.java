package com.yonghui.portal.controller.fresh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.service.fresh.FreshReportsService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//生鲜报表
@RestController
@RequestMapping(value="freshReports")
public class FreshReportsController {

	Logger log=Logger.getLogger(this.getClass());
	
	@Reference
	private FreshReportsService freshReportsService;

	/*******************             销售概况            *******************/

	//报表图
	@ResponseBody
	@RequestMapping(value="marketReort")
	public R marketReport(HttpServletResponse response,String sDate,String eDate){

		response.setHeader("Access-Control-Allow-Origin", "*");

		Map<String, Object> map = new HashMap<>();
		map.put("sDate", sDate);
		map.put("eDate", eDate);
		List<Map<String,Object>> list = freshReportsService.shellSurveyReports(map);


		List<Double> oneShellYoY = new ArrayList<Double>();//一集群同比  销售额同比
		List<Double> twoShellYoY = new ArrayList<Double>();//二集群同比
		List<Double> oneShellHold = new ArrayList<Double>();//一集群  销售金额
		List<Double> twoShellHold = new ArrayList<Double>();//二集群

		List<Double> oneProfitYoY = new ArrayList<Double>();//一集群同比  毛利额同比
		List<Double> twoProfitYoY = new ArrayList<Double>();//二集群同比
		List<Double> oneProfitHold = new ArrayList<Double>();// 一集群   毛利额
		List<Double> twoProfitHold = new ArrayList<Double>();//二集群

		List<Double> oneCustomerYoY = new ArrayList<Double>();//一集群同比     客流同比
		List<Double> twoCustomerYoY = new ArrayList<Double>();//二集群同比
		List<Double> oneCustomerHold = new ArrayList<Double>();//一集群    客流
		List<Double> twoCustomerHold = new ArrayList<Double>();//二集群
		//List<String> xdata = new ArrayList<String>();
		int length = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("holdName").equals("第一集群")){
				length++;
			}
		}
		String[] xdata = new String[length];

		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("holdName").equals("第一集群")){
				oneShellYoY.add(Double.parseDouble(list.get(i).get("saleQTB").toString()));//销售金额同比
				oneShellHold.add(Double.parseDouble(list.get(i).get("saleAmount").toString()));//销售金额

				oneProfitYoY.add(Double.parseDouble(list.get(i).get("profitQTB").toString()));
				oneProfitHold.add(Double.parseDouble(list.get(i).get("profit").toString()));

				oneCustomerYoY.add(Double.parseDouble(list.get(i).get("customerQTB").toString()));
				oneCustomerHold.add(Double.parseDouble(list.get(i).get("customer").toString()));

				xdata[i] = list.get(i).get("className").toString();

			}else{//第二集群

				twoShellYoY.add(Double.parseDouble(list.get(i).get("saleQTB").toString()));
				twoShellHold.add(Double.parseDouble(list.get(i).get("saleAmount").toString()));

				twoProfitYoY.add(Double.parseDouble(list.get(i).get("profitQTB").toString()));
				twoProfitHold.add(Double.parseDouble(list.get(i).get("profit").toString()));

				twoCustomerYoY.add(Double.parseDouble(list.get(i).get("customerQTB").toString()));
				twoCustomerHold.add(Double.parseDouble(list.get(i).get("customer").toString()));
			}
		}

		Map<String,Object> option = new HashMap<String,Object>();
		option.put("oneShellYoY", oneShellYoY);
		option.put("twoShellYoY", twoShellYoY);
		option.put("oneShellHold", oneShellHold);
		option.put("twoShellHold", twoShellHold);

		option.put("oneProfitYoY", oneProfitYoY);
		option.put("twoProfitYoY", twoProfitYoY);
		option.put("oneProfitHold", oneProfitHold);
		option.put("twoProfitHold", twoProfitHold);

		option.put("oneCustomerYoY", oneCustomerYoY);
		option.put("twoCustomerYoY", twoCustomerYoY);
		option.put("oneCustomerHold", oneCustomerHold);
		option.put("twoCustomerHold", twoCustomerHold);

		option.put("xdata", xdata);

		if(option!=null){
			log.info(option);
			return R.success(JSONObject.toJSONString(option));
		}else{
			return R.error();
		}

	}


	//销售概况下三个表格数据
	@ResponseBody
	@RequestMapping(value="shellSurvey")
	public R shellSurvey(HttpServletResponse response,String sDate,String eDate,String areaName,
			String className,String areaMans,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<>();
		map.put("sDate",sDate); 
		map.put("eDate", eDate);
		map.put("areaName", areaName);
		map.put("className", className);
		map.put("areaMans", areaMans);
		map.put("holdName", holdName);
		List<Map<String, Object>> list = freshReportsService.shellSurvey(map);
		if(list.size() > 0){
			return R.success(list);
		}else{
			return R.error();
		}
	}
	

	
	
	/*******************             损耗分析            *******************/
	//损耗概况图表
	@ResponseBody
	@RequestMapping(value="lossSurveyReports")
	public R lossSurveyReports(HttpServletResponse response,String sDate,String eDate){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sDate", sDate);
		map.put("eDate", eDate);
		map.put("areaName", null);
		map.put("className", null);
		map.put("areaMans", null);
		
		List<Map<String,Object>> list = freshReportsService.lossSurveyList(map);
		List<Double> oneWastageAmount = new ArrayList<Double>();//损耗额
		List<Double> twoWastageAmount = new ArrayList<Double>();
		List<Double> oneWastageRate = new ArrayList<Double>();//损耗率
		List<Double> twoWastageRate = new ArrayList<Double>();

		String[] xdata = new String[]{"活鲜课","贝类课","冰鲜课","猪肉课","家禽课","蔬菜课","水果课","干货课"};

		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("holdName").equals("第一集群")){
				oneWastageAmount.add(Math.abs(Double.parseDouble(list.get(i).get("wastageAmount").toString())));
				oneWastageRate.add(Math.abs(Double.parseDouble(list.get(i).get("wastageRate").toString())));
			}else{
				twoWastageAmount.add(Math.abs(Double.parseDouble(list.get(i).get("wastageAmount").toString())));
				twoWastageRate.add(Math.abs(Double.parseDouble(list.get(i).get("wastageRate").toString())));
				//xdata[i] = list.get(i).get("className").toString();
			}
		}

		Map<String,Object> option = new HashMap<String,Object>();
		option.put("oneWastageAmount", oneWastageAmount);
		option.put("twoWastageAmount", twoWastageAmount);
		option.put("oneWastageRate", oneWastageRate);
		option.put("twoWastageRate", twoWastageRate);
		option.put("xdata", xdata);

		if(option!=null){
			return R.success(JSONObject.toJSONString(option));
		}else{
			return R.error();
		}
	}
	
	
	//损耗概况报表
	@ResponseBody
	@RequestMapping(value="lossSurvey")
	public R lossSurveyList(HttpServletResponse response,String sDate,String eDate,String areaName,String className,
							String areaMans,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sDate", sDate);
		map.put("eDate", eDate);
		map.put("areaName", areaName);
		map.put("className", className);
		map.put("areaMans", areaMans);
		map.put("holdName", holdName);
		List<Map<String,Object>> list = freshReportsService.lossSurvey(map);
		if(list.size() > 0){
			return R.success(list);
		}else{
			return R.error();
		}
		
	}
	/*******************             负毛利            *******************/
	
	//负毛利概况
	@ResponseBody
	@RequestMapping(value="minusProfitSurvey")
	public R minusProfitSurvey(HttpServletResponse response,String sDate,String eDate,String areaName,String className,
							   String areaMans,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sDate", sDate);
		map.put("eDate", eDate);
		map.put("areaName", areaName);
		map.put("className", className);
		map.put("areaMans", areaMans);
		map.put("holdName", holdName);
		List<Map<String,Object>> list = freshReportsService.minusProfitSurvey(map);
		if(list.size() > 0){
			return R.success(list);
		}else{
			return R.error();
		}
	}
	

}
