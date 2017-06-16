package com.yonghui.portal.controller.fresh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.service.fresh.FreshReportsService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.fresh.SaleAreaExport;
import com.yonghui.portal.util.fresh.SaleShopExport;
import com.yonghui.portal.util.fresh.WastageAreaExport;
import com.yonghui.portal.util.fresh.WastageShopExport;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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
	@RequestMapping(value="saleAreaExcel" ,method = RequestMethod.GET)
	public void saleAreaExcel(HttpServletResponse response , String sDate,String eDate,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");

		try{
			Map<String, Object> map = new HashMap<>();
			map.put("sDate", sDate);
			map.put("eDate", eDate);
			map.put("holdName",holdName);
			List<Map<String,Object>> list = freshReportsService.areaExcel(map);
			SaleAreaExport areaExport = new SaleAreaExport();
			HSSFWorkbook workbook = areaExport.exportByHydBudget(list);
			String filename = "SaleAreaExport.xls";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}catch (Exception e){
			log.error("导出excel异常", e);
		}
	}

	@RequestMapping(value="saleShopExcel",method = RequestMethod.GET)
	public void saleShopExcel(HttpServletResponse response,String sDate,String eDate,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");

		try{
			Map<String, Object> map = new HashMap<>();
			map.put("sDate", sDate);
			map.put("eDate", eDate);
			map.put("holdName",holdName);
			List<Map<String,Object>> list = freshReportsService.shopExcel(map);
			SaleShopExport shopExport = new SaleShopExport();
			HSSFWorkbook workbook = shopExport.exportByHydBudget(list);
			String filename = "SaleShopExport.xls";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		}catch (Exception e){
			log.error("导出excel异常", e);
		}
	}

	
	/*******************             损耗分析            *******************/

	@RequestMapping(value="wastageAreaExcel",method = RequestMethod.GET)
	public void wastageAreaExcel(HttpServletResponse response,String sDate,String eDate,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");

		try{
			Map<String, Object> map = new HashMap<>();
			map.put("sDate", sDate);
			map.put("eDate", eDate);
			map.put("holdName",holdName);
			List<Map<String,Object>> list = freshReportsService.wastageAreaExcel(map);
			WastageAreaExport export = new WastageAreaExport();
			HSSFWorkbook workbook = export.exportByHydBudget(list);
			String filename = "WastageAreaExport.xls";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		}catch (Exception e){
			log.error("导出excel异常", e);
		}
	}


	@RequestMapping(value="wastageShopExcel",method = RequestMethod.GET)
	public void wastageShopExcel(HttpServletResponse response,String sDate,String eDate,String holdName){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");

		try{
			Map<String, Object> map = new HashMap<>();
			map.put("sDate", sDate);
			map.put("eDate", eDate);
			map.put("holdName",holdName);
			List<Map<String,Object>> list = freshReportsService.wastageShopExcel(map);
			WastageShopExport export = new WastageShopExport();
			HSSFWorkbook workbook = export.exportByHydBudget(list);
			String filename = "WastageShopExport.xls";
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		}catch (Exception e){
			log.error("导出excel异常", e);
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
