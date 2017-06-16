package com.yonghui.portal.util.horse;

import com.yonghui.portal.model.horse.HorseSort;
import org.apache.poi.hssf.usermodel.*;

import java.util.List;




/**
 * 操作Excel表格的功能类
 */
public class horseExport {
	/*
	 * 赛马结果分析
	 */
	public HSSFWorkbook exportByHydBudget(List<HorseSort> list) throws Exception {

		// 创建excel
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建sheet
		HSSFSheet sheet = wb.createSheet("赛马结果分析");

		// 创建一行
		HSSFRow rowTitle = sheet.createRow(0);

		// 创建标题栏样式
		HSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		HSSFFont fontTitle = wb.createFont();
		// 宋体加粗
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTitle.setFontName("宋体");
		fontTitle.setFontHeight((short) 200);
		styleTitle.setFont(fontTitle);

		// 在行上创建1列
		HSSFCell cellTitle = rowTitle.createCell(0);

		// 在行上创建列
		cellTitle.setCellValue("日期");
		cellTitle.setCellStyle(styleTitle);
		int cellInde = 0;
		
		// 在行上创建列
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("店编");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("店名");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("小店ID");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("小店名称");
		cellTitle.setCellStyle(styleTitle);		

		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("说明");
		cellTitle.setCellStyle(styleTitle);		
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("基础分");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("值");
		cellTitle.setCellStyle(styleTitle);
		
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("对标值");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("得分");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("均值上下标记");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("排名");
		cellTitle.setCellStyle(styleTitle);
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("档次");
		cellTitle.setCellStyle(styleTitle);
		
		
		cellTitle = rowTitle.createCell(++cellInde);
		cellTitle.setCellValue("参与排名门店数");
		cellTitle.setCellStyle(styleTitle);
		

		HSSFCellStyle styleCenter = wb.createCellStyle();
		styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

		for (int i = 0; i < list.size(); i++) {
			HorseSort item = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			cellInde = 0;
			HSSFCell cell = row.createCell(cellInde);
			
			cell.setCellValue(item.getLkpDate());
			cell.setCellStyle(styleCenter);
			
			// 大店Id
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getShopId());
			cell.setCellStyle(styleCenter);
			// 大店名称
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getShopName());
			cell.setCellStyle(styleCenter);
		
			// 小店ID
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getGroupId());
			cell.setCellStyle(styleCenter);
			// 小店名称
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getGroupName());
			cell.setCellStyle(styleCenter);
			// 说明
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getResult());
			cell.setCellStyle(styleCenter);			
			// 基础分
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getAllScore());
			cell.setCellStyle(styleCenter);
			// 值
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getTheValue());
			cell.setCellStyle(styleCenter);
			// 对标值
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getTheValueComp());
			cell.setCellStyle(styleCenter);
			// 得分
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getScore());
			cell.setCellStyle(styleCenter);
			// 均值上下标记
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getUpDown());
			cell.setCellStyle(styleCenter);
			// 排名
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getSortID());
			cell.setCellStyle(styleCenter);
			// 排名档次
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getAbc());
			cell.setCellStyle(styleCenter);			
			// 参与排名门店数
			cell = row.createCell(++cellInde);
			cell.setCellValue(item.getShopNum());
			cell.setCellStyle(styleCenter);				
			
		}

		return wb;
	}

}
