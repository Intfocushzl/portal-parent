package com.yonghui.portal.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ExportExcelUtils<T> {
	
    public static String getResourcePath(Class clazz) {
        String className = clazz.getName();
        
        String classNamePath = className.replace(".", "/") + ".class";
        URL is = clazz.getClassLoader().getResource(classNamePath);
        String path = is.getFile();
        path = StringUtils.replace(path, "%20", " ");
        path = StringUtils.replace(path, StringUtils.replace(className , clazz.getPackage().getName() + ".", "") + ".class", "");
        return path;
    }
    
    public  HSSFWorkbook exportExcel( String xmlFileName , Class clazz  , String exportPath , List<T> listcol   ){

        //获取解析xml文件路径
        String path = getResourcePath(clazz) +  xmlFileName;
        File file = new  File(path);
        SAXBuilder builder = new SAXBuilder();
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            //解析xml文件
            Document parse = builder.build(file);
            //创建Excel
            //创建sheet
            HSSFSheet sheet = wb.createSheet();

            //获取xml文件跟节点
            Element root = parse.getRootElement();
            //获取模板名称
            String templateName = root.getAttribute("name").getValue();
            int rownum = 0;
            int column = 0;
            //设置列宽
            Element colgroup = root.getChild("colgroup");
            setColumnWidth(sheet,colgroup);

            //设置标题
            Element title = root.getChild("title");
            List<Element> trs = title.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                List<Element> tds = tr.getChildren("td");
                HSSFRow row = sheet.createRow(rownum);
                HSSFCellStyle cellStyle = wb.createCellStyle();//创建单元格样式
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置单元格对齐方式
                for(column = 0;column <tds.size();column ++){
                    Element td = tds.get(column);
                    HSSFCell cell = row.createCell(column);
                    Attribute rowSpan = td.getAttribute("rowspan");
                    Attribute colSpan = td.getAttribute("colspan");
                    Attribute value = td.getAttribute("value");
                    if(value !=null){
                        String val = value.getValue();
                        cell.setCellValue(val);
                        int rspan = rowSpan.getIntValue() - 1;//execel的行以0开头
                        int cspan = colSpan.getIntValue() -1;

                        //设置字体
                        HSSFFont font = wb.createFont();
                        font.setFontName("仿宋_GB2312");
                        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
//                        font.setFontHeight((short)12);
                        font.setFontHeightInPoints((short)12);
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                        //合并单元格居中
                        sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));
                    }
                }
                rownum ++;
            }
            //设置表头
            Element thead = root.getChild("thead");
            trs = thead.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                HSSFRow row = sheet.createRow(rownum);
                List<Element> ths = tr.getChildren("th");
                for(column = 0;column < ths.size();column++){
                    Element th = ths.get(column);
                    Attribute valueAttr = th.getAttribute("value");
                    HSSFCell cell = row.createCell(column);
                    if(valueAttr != null){
                        String value =valueAttr.getValue();
                        cell.setCellValue(value);
                    }
                }
                rownum++;
            }

            //设置数据区域样式
            Element tbody = root.getChild("tbody");
            Element tr = tbody.getChild("tr");

            
            
            List<Element> tds = tr.getChildren("td");
            
            List<HSSFCellStyle> styles = getType(wb ,tds );
            try {
				for(T t : listcol){
					//取出每个记录
					HSSFRow row = sheet.createRow(rownum);
				    for(column =0 ;column < tds.size();column++){
				        //取出的excel配置的每一列，进行配置的读取，根据配置来将记录里面的数据取出来。
				    	Element td = tds.get(column);
				        HSSFCell cell = row.createCell(column);
				        cell.setCellStyle(styles.get(column));
				        setValue(wb,cell,td , t);
				    }
					
				    rownum++;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb ;
    	
    }
    
    
    public  XSSFWorkbook exportXSSFExcelWithPath( String xmlFileName , Class clazz  , String exportPath , List<T> listcol, String abnormalxmlpath   ){

        //获取解析xml文件路径
       // String path = getResourcePath(clazz) +  xmlFileName;
    	String path = abnormalxmlpath +  xmlFileName;
        File file =  new File(path);
        SAXBuilder builder = new SAXBuilder();
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            //解析xml文件
            Document parse = builder.build(file);
            //创建Excel
            //创建sheet
            XSSFSheet sheet = wb.createSheet();

            //获取xml文件跟节点
            Element root = parse.getRootElement();
            //获取模板名称
            String templateName = root.getAttribute("name").getValue();
            int rownum = 0;
            int column = 0;
            //设置列宽
            Element colgroup = root.getChild("colgroup");
            setXSSFColumnWidth(sheet,colgroup);

            //设置标题
            Element title = root.getChild("title");
            List<Element> trs = title.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                List<Element> tds = tr.getChildren("td");
                XSSFRow row = sheet.createRow(rownum);
                XSSFCellStyle cellStyle = wb.createCellStyle();//创建单元格样式
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置单元格对齐方式
                for(column = 0;column <tds.size();column ++){
                    Element td = tds.get(column);
                    XSSFCell cell = row.createCell(column);
                    Attribute rowSpan = td.getAttribute("rowspan");
                    Attribute colSpan = td.getAttribute("colspan");
                    Attribute value = td.getAttribute("value");
                    if(value !=null){
                        String val = value.getValue();
                        cell.setCellValue(val);
                        int rspan = rowSpan.getIntValue() - 1;//execel的行以0开头
                        int cspan = colSpan.getIntValue() -1;

                        //设置字体
                        XSSFFont font = wb.createFont();
                        font.setFontName("仿宋_GB2312");
                        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
//                        font.setFontHeight((short)12);
                        font.setFontHeightInPoints((short)12);
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                        //合并单元格居中
                        sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));
                    }
                }
                rownum ++;
            }
            //设置表头
            Element thead = root.getChild("thead");
            trs = thead.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                XSSFRow row = sheet.createRow(rownum);
                List<Element> ths = tr.getChildren("th");
                for(column = 0;column < ths.size();column++){
                    Element th = ths.get(column);
                    Attribute valueAttr = th.getAttribute("value");
                    XSSFCell cell = row.createCell(column);
                    if(valueAttr != null){
                        String value =valueAttr.getValue();
                        cell.setCellValue(value);
                    }
                }
                rownum++;
            }

            //设置数据区域样式
            Element tbody = root.getChild("tbody");
            Element tr = tbody.getChild("tr");

            
            
            List<Element> tds = tr.getChildren("td");
            List<XSSFCellStyle> styles = getXSSFType(wb ,tds );
            try {
				for(T t : listcol){
					//取出每个记录
					XSSFRow row = sheet.createRow(rownum);
				    for(column =0 ;column < tds.size();column++){
				        //取出的excel配置的每一列，进行配置的读取，根据配置来将记录里面的数据取出来。
				    	Element td = tds.get(column);
				        XSSFCell cell = row.createCell(column);
				        cell.setCellStyle(styles.get(column));
				        setXSSFValue(wb,cell,td , t);
				    }
					if(rownum%10000 == 0){
						Thread.sleep(10000);
						//System.gc();
					}
				    rownum++;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
            


        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb ;
    	
    }
    public  XSSFWorkbook exportXSSFExcel( String xmlFileName , Class clazz  , String exportPath , List<T> listcol   ){

        //获取解析xml文件路径
        String path = getResourcePath(clazz) +  xmlFileName;
        File file = new  File(path);
        SAXBuilder builder = new SAXBuilder();
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            //解析xml文件
            Document parse = builder.build(file);
            //创建Excel
            //创建sheet
            XSSFSheet sheet = wb.createSheet();

            //获取xml文件跟节点
            Element root = parse.getRootElement();
            //获取模板名称
            String templateName = root.getAttribute("name").getValue();
            int rownum = 0;
            int column = 0;
            //设置列宽
            Element colgroup = root.getChild("colgroup");
            setXSSFColumnWidth(sheet,colgroup);

            //设置标题
            Element title = root.getChild("title");
            List<Element> trs = title.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                List<Element> tds = tr.getChildren("td");
                XSSFRow row = sheet.createRow(rownum);
                XSSFCellStyle cellStyle = wb.createCellStyle();//创建单元格样式
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置单元格对齐方式
                for(column = 0;column <tds.size();column ++){
                    Element td = tds.get(column);
                    XSSFCell cell = row.createCell(column);
                    Attribute rowSpan = td.getAttribute("rowspan");
                    Attribute colSpan = td.getAttribute("colspan");
                    Attribute value = td.getAttribute("value");
                    if(value !=null){
                        String val = value.getValue();
                        cell.setCellValue(val);
                        int rspan = rowSpan.getIntValue() - 1;//execel的行以0开头
                        int cspan = colSpan.getIntValue() -1;

                        //设置字体
                        XSSFFont font = wb.createFont();
                        font.setFontName("仿宋_GB2312");
                        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
//                        font.setFontHeight((short)12);
                        font.setFontHeightInPoints((short)12);
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                        //合并单元格居中
                        sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));
                    }
                }
                rownum ++;
            }
            //设置表头
            Element thead = root.getChild("thead");
            trs = thead.getChildren("tr");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                XSSFRow row = sheet.createRow(rownum);
                List<Element> ths = tr.getChildren("th");
                for(column = 0;column < ths.size();column++){
                    Element th = ths.get(column);
                    Attribute valueAttr = th.getAttribute("value");
                    XSSFCell cell = row.createCell(column);
                    if(valueAttr != null){
                        String value =valueAttr.getValue();
                        cell.setCellValue(value);
                    }
                }
                rownum++;
            }

            //设置数据区域样式
            Element tbody = root.getChild("tbody");
            Element tr = tbody.getChild("tr");

            
            
            List<Element> tds = tr.getChildren("td");
            List<XSSFCellStyle> styles = getXSSFType(wb ,tds );
            try {
				for(T t : listcol){
					//取出每个记录
					XSSFRow row = sheet.createRow(rownum);
				    for(column =0 ;column < tds.size();column++){
				        //取出的excel配置的每一列，进行配置的读取，根据配置来将记录里面的数据取出来。
				    	Element td = tds.get(column);
				        XSSFCell cell = row.createCell(column);
				        cell.setCellStyle(styles.get(column));
				        setXSSFValue(wb,cell,td , t);
				    }
					if(rownum%10000 == 0){
						Thread.sleep(10000);
						//System.gc();
					}
				    rownum++;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
            


        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb ;
    	
    }
    
    /*
     * 设置单元格数据类型
     * 
     * 
     * */
    private static List<HSSFCellStyle>  getType(HSSFWorkbook wb, List<Element> tds  ) {


        //HSSFDataformat
        List<HSSFCellStyle> styles = new ArrayList<HSSFCellStyle>();
        for(Element  td : tds ){
          Attribute typeAttr = td.getAttribute("type");
          String type = typeAttr.getValue();
          HSSFDataFormat format = wb.createDataFormat();
          HSSFCellStyle cellStyle = wb.createCellStyle();
          if("NUMERIC".equalsIgnoreCase(type)){
            Attribute formatAttr = td.getAttribute("format");
            String formatValue = formatAttr.getValue();
            formatValue = StringUtils.isNotBlank(formatValue)? formatValue : "#,##0.00";
            cellStyle.setDataFormat(format.getFormat(formatValue));
          }else if("STRING".equalsIgnoreCase(type)){
            cellStyle.setDataFormat(format.getFormat("@"));
        }else if("DATE".equalsIgnoreCase(type)){
            cellStyle.setDataFormat(format.getFormat("yyyy-m-d"));
        }
          styles.add(cellStyle);
        }
        return styles;

    }    
    
    /*
     * 设置单元格数据类型
     * 
     * 
     * */
    private static List<XSSFCellStyle>  getXSSFType(XSSFWorkbook wb, List<Element> tds  ) {


        //HSSFDataformat
        List<XSSFCellStyle> styles = new ArrayList<XSSFCellStyle>();
        for(Element  td : tds ){
          Attribute typeAttr = td.getAttribute("type");
          String type = typeAttr.getValue();
          XSSFDataFormat format = wb.createDataFormat();
          XSSFCellStyle cellStyle = wb.createCellStyle();
          if("NUMERIC".equalsIgnoreCase(type)){
            Attribute formatAttr = td.getAttribute("format");
            String formatValue = formatAttr.getValue();
            formatValue = StringUtils.isNotBlank(formatValue)? formatValue : "#,##0.00";
            cellStyle.setDataFormat(format.getFormat(formatValue));
          }else if("STRING".equalsIgnoreCase(type)){
            cellStyle.setDataFormat(format.getFormat("@"));
        }else if("DATE".equalsIgnoreCase(type)){
            cellStyle.setDataFormat(format.getFormat("yyyy-m-d"));
        }
          styles.add(cellStyle);
        }
        return styles;

    }    
    
     /**
      * 	
     * 设置单元格内的数据
     * @author David
     * @param wb
     * @param cell
     * @param td
     */
    private static void setValue(HSSFWorkbook wb, HSSFCell cell, Element td , Object record ) {
        //设置cell的值
        Attribute columnAttr = td.getAttribute("column");
        Attribute typeAttr = td.getAttribute("type");
        
        String type = typeAttr.getValue();
        
        String column = columnAttr.getValue();    
        Object param = getFieldValueByName(column , record);
        
        if("NUMERIC".equalsIgnoreCase(type)){
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
          }else if("STRING".equalsIgnoreCase(type)){
            cell.setCellValue("");
        }else if("DATE".equalsIgnoreCase(type)){
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        }
    
        
        if (param instanceof Integer) {
            int value = ((Integer) param).intValue();
            cell.setCellValue(value);
           } else if (param instanceof String) {
            String s = (String) param;
            cell.setCellValue(s);
           } else if (param instanceof Double) {
            double d = ((Double) param).doubleValue();
            cell.setCellValue(d);
           } else if (param instanceof Float) {
            float f = ((Float) param).floatValue();
            cell.setCellValue(f);
           } else if (param instanceof Long) {
            long l = ((Long) param).longValue();
            cell.setCellValue(l);
           } else if (param instanceof Boolean) {
            boolean b = ((Boolean) param).booleanValue();
            cell.setCellValue(b);
           } else if (param instanceof Date) {
            Date d = (Date) param;
            cell.setCellValue(d);
           }
    }
    
    
    /**
     * 	
    * 设置单元格内的数据
    * @author David
    * @param wb
    * @param cell
    * @param td
    */
   private static void setXSSFValue(XSSFWorkbook wb, XSSFCell cell, Element td , Object record ) {
       //设置cell的值
       Attribute columnAttr = td.getAttribute("column");
       Attribute typeAttr = td.getAttribute("type");
       
       String type = typeAttr.getValue();
       
       String column = columnAttr.getValue();    
       Object param = getFieldValueByName(column , record);
       
       if("NUMERIC".equalsIgnoreCase(type)){
           cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
         }else if("STRING".equalsIgnoreCase(type)){
           cell.setCellValue("");
       }else if("DATE".equalsIgnoreCase(type)){
           cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
       }
   
       
       if (param instanceof Integer) {
           int value = ((Integer) param).intValue();
           cell.setCellValue(value);
          } else if (param instanceof String) {
           String s = (String) param;
           cell.setCellValue(s);
          } else if (param instanceof Double) {
           double d = ((Double) param).doubleValue();
           cell.setCellValue(d);
          } else if (param instanceof Float) {
           float f = ((Float) param).floatValue();
           cell.setCellValue(f);
          } else if (param instanceof Long) {
           long l = ((Long) param).longValue();
           cell.setCellValue(l);
          } else if (param instanceof Boolean) {
           boolean b = ((Boolean) param).booleanValue();
           cell.setCellValue(b);
          } else if (param instanceof Date) {
           Date d = (Date) param;
           cell.setCellValue(d);
          }
   }
    
	   private static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});  
	           
	           return value;    
	       } catch (Exception e) {    
	    	   e.printStackTrace();
	           return null;    
	       }    
	   }  
    /**
     * 设置列宽
     * @author David
     * @param sheet
     * @param colgroup
     */
    private static void setColumnWidth(HSSFSheet sheet, Element colgroup) {
        List<Element> cols = colgroup.getChildren("col");
        for (int i = 0; i < cols.size(); i++) {
            Element col = cols.get(i);
            Attribute width = col.getAttribute("width");
            String unit = width.getValue().replaceAll("[0-9,\\.]", "");//截取单位
            String value = width.getValue().replaceAll(unit, "");//擦除单位
            int v=0;
            //单位转化
            if(StringUtils.isBlank(unit) || "px".endsWith(unit)){//如果单位为空或等于px
                v = Math.round(Float.parseFloat(value) * 37F);
            }else if ("em".endsWith(unit)){//如果单位为em
                v = Math.round(Float.parseFloat(value) * 267.5F);
            }
            sheet.setColumnWidth(i, v);//设置第i列宽度为v
        }
    }

    /**
     * 设置列宽
     * @author David
     * @param sheet
     * @param colgroup
     */
    private static void setXSSFColumnWidth(XSSFSheet sheet, Element colgroup) {
        List<Element> cols = colgroup.getChildren("col");
        for (int i = 0; i < cols.size(); i++) {
            Element col = cols.get(i);
            Attribute width = col.getAttribute("width");
            String unit = width.getValue().replaceAll("[0-9,\\.]", "");//截取单位
            String value = width.getValue().replaceAll(unit, "");//擦除单位
            int v=0;
            //单位转化
            if(StringUtils.isBlank(unit) || "px".endsWith(unit)){//如果单位为空或等于px
                v = Math.round(Float.parseFloat(value) * 37F);
            }else if ("em".endsWith(unit)){//如果单位为em
                v = Math.round(Float.parseFloat(value) * 267.5F);
            }
            sheet.setColumnWidth(i, v);//设置第i列宽度为v
        }
    }
    /**
     * 方法名称：SetDataValidation
     * 内容摘要：设置数据有效性
     * @param  sheet excel sheet內容
     * @param textList 下拉列表
     * @param firstRow 單元格範圍
     * @param firstCol
     * @param endRow
     * @param endCol
     */
    private static HSSFDataValidation setDataValidation(HSSFSheet sheet,String[] textList,short firstRow,short firstCol, short endRow, short endCol) {
        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        //设置数据有效性加载在哪个单元格上。
        //四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
        //数据有效性对象
        HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation);
        return data_validation;
    }
}