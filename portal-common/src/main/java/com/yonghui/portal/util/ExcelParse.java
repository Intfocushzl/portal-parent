package com.yonghui.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * 
 * @author shengwm
 * 
 */
public class ExcelParse {

	private Workbook wb = null;
	private Map<String, List<String[]>> map=null;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		String path = "d:\\发货明细.xlsx";
		FileInputStream fis = new FileInputStream(path);
		ExcelParse ep = new ExcelParse();
		try {
			
			 List<String[]> list= ep.reader(fis, 0);;
			 for (String[] arr : list) {
			 for (String s : arr) {
			 System.out.print(s+",");
			 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.exit(0);
	}

	private void initWorkbook(InputStream is) throws IOException {
		if (!is.markSupported()) {
			is = new PushbackInputStream(is, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(is)) {
			wb = new HSSFWorkbook(is);
		} else if (POIXMLDocument.hasOOXMLHeader(is)) {
			try {
				wb = new XSSFWorkbook(OPCPackage.open(is));
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("你的Excel版本太奇葩了，目前的科技还无法解析!");
		}
	}

	public void reader(String path, int startRow) throws IOException {
		FileInputStream fis = new FileInputStream(new File(path));
		reader(fis, startRow);
	}

	public void reader(File file, int startRow) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		reader(fis, startRow);
	}

	public List<String[]> reader(InputStream is, int startRow) throws IOException {
		initWorkbook(is);
		List<String[]> dataList = null;
		map=new HashMap<String, List<String[]>>();
		for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
			Sheet sheet = wb.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}

			String key = sheet.getSheetName();
			if (!map.containsKey(key)) {
				dataList = new ArrayList<String[]>();
				map.put(key, dataList);
			}

			// 循环行Row
			for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				String[] rows = new String[row.getLastCellNum() + 1];
				// 循环列Cell
				for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
					Cell cell = row.getCell(cellNum);
					if (cell == null) {
						continue;
					}
					rows[cellNum] = getCellValue(cell);
				}
				if (isNotEmpty(rows))
					dataList.add(rows);
			}
		}
		return dataList;
	}

	@SuppressWarnings("static-access")
	private String getCellValue(Cell cell) {
		int cellType = cell.getCellType();
		if (cellType == cell.CELL_TYPE_BOOLEAN) {
			return cell.toString();
		} else if (cellType == cell.CELL_TYPE_STRING) {
			return cell.toString();
		} else if (cellType == cell.CELL_TYPE_FORMULA) {
			String value=null;
			switch (cell.getCachedFormulaResultType()){
            case HSSFCell.CELL_TYPE_STRING:
                value= cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue()+"";
                break;
                default:
            }
			return value;
		} else if (cellType == cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				return CalendarUtils.getFormatDate("yyyy-MM-dd HH:mm:ss", date);
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return cell.toString();
			}
		} else {
			return cell.toString();
		}
	}

	public void writer() {

	}

	/**
	 * 判断对象数组内容是否不为空
	 * 
	 * @param arr
	 * @return
	 */
	public static <T> Boolean isNotEmpty(T[] arr) {
		Boolean flag = false;
		for (T t : arr) {
			if (t != null && !"".equals(t))
				flag = true;
		}
		return flag;
	}
	
	 //使用POI创建excel工作簿 
    public static HSSFWorkbook createWorkBook(String path,String sheetName) throws Exception { 
        //创建excel工作簿 
    	HSSFWorkbook wb = new HSSFWorkbook(); 
        //创建第一个sheet（页），命名为 new sheet 
    	HSSFSheet sheet = wb.createSheet(sheetName); 

     	// 创建标题栏样式
     	HSSFCellStyle styleTitle = wb.createCellStyle();
     	styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
     	HSSFFont fontTitle = wb.createFont();
     	// 宋体加粗
     	fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
     	fontTitle.setFontName("宋体");
     	fontTitle.setFontHeight((short) 200);
     	styleTitle.setFont(fontTitle);
        
        // 创建一行，在页sheet上 
     	HSSFRow row = sheet.createRow((short) 0); 
        // 在row行上创建一个方格 
        HSSFCell cellTitle = null;
        List<String> headers = readWorkBook(path);
        for(int i = 0;i < headers.size();i++){
        	String header = headers.get(i);
        	cellTitle =row.createCell(i);
        	cellTitle.setCellValue(header);
        	cellTitle.setCellStyle(styleTitle);
         }
        return wb;
    } 
 
    //使用POI读入excel工作簿文件 
    public static List<String> readWorkBook(String path) throws Exception { 
        // poi读取excel 
        //创建要读入的文件的输入流 S
        InputStream inp = new FileInputStream(path); 
         
        //根据上述创建的输入流 创建工作簿对象 
        Workbook wb = WorkbookFactory.create(inp); 
        //得到第一页 sheet 
        //页Sheet是从0开始索引的 
        Sheet sheet = wb.getSheetAt(0); 
        Row row0 =  sheet.getRow(0);
       List<String> headers = new ArrayList<>();
        for (Cell cell : row0) {
        	headers.add(cell.toString());
        	 
		}
       // System.out.print(headers); 
        //关闭输入流 
        inp.close(); 
        return headers;
    } 

	public Map<String, List<String[]>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String[]>> map) {
		this.map = map;
	}
}
