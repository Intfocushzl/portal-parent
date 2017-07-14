package com.yonghui.portal.util.poster;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.sys.PosterImportArea;
import com.yonghui.portal.model.sys.PosterImportGoods;
import com.yonghui.portal.service.sys.PosterImportService;
import com.yonghui.portal.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 海报导入
 */
@Component("posterImportUtil")
public class PosterImportUtil {

    Logger log = Logger.getLogger(this.getClass());

    public int totalCells;

    @Reference
    public PosterImportService posterImportService;

    /**
     * @throws Exception
     * @author liuwei
     * <p>
     */
    public List<PosterImportArea> PosterImportArea(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getLastRowNum();// 所有行
        // 得到Excel的最大的一列(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        } else {
            throw new Exception("请按照模板填写数据后上传");
        }
        List<PosterImportArea> areaList = new ArrayList<PosterImportArea>();
        PosterImportArea area;
        Row row;
        GoodsConstants util = new GoodsConstants();
        // 循环Excel行数,从第二行开始。
        for (int r = 1; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            area = new PosterImportArea();
            // 循环Excel的列
            List<Map<String, Object>> list = posterImportService.areamansList();
            Map<String, Object> map = new HashMap<String, Object>();
            for (Map<String, Object> item : list) {
                if(map.get(item.get("City")) == null){
                    map.put(item.get("City").toString(), item);
                }
            }
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 判断是否为空
                    boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                    if (isNull) {
                        throw new Exception("填写的数据含有空值");
                    }
                    // 判断是否全部是数字
                    if (c == 0) {
                        try {
                            util.isNumber(cell.getStringCellValue().trim());
                        } catch (Exception e) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列有非数字的数据");
                        }
                        area.setPosterId(Integer.parseInt(cell.getStringCellValue().trim()));
                    } else if (c == 1) {
                        area.setArea(cell.getStringCellValue().trim());
                    } else if (c == 2) {
                        if(map.get(cell.getStringCellValue().trim()) != null){
                            area.setCity(cell.getStringCellValue().trim());
                        }else{
                            throw new Exception("填写的数据有误！！" + "第" + r + "行" + (c + 1) + "城市不正确");
                        }

                    }
                } else {
                    throw new Exception("填写的数据含有空值！！" + "第" + r + "行" + (c + 1) + "列有空值");
                }
            }
            // 添加数据
            areaList.add(area);
        }
        return areaList;
    }


    public List<PosterImportGoods> PosterImportGoods(Workbook wb) throws Exception {
        //查询所有的posterId
        List<Map<String, Object>> list = posterImportService.areaList();
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getLastRowNum();// 所有行
        // 得到Excel的最大的一列(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        } else {
            throw new Exception("请按照模板填写数据后上传");
        }
        List<PosterImportGoods> goodsList = new ArrayList<PosterImportGoods>();
        PosterImportGoods goods;
        Row row;
        GoodsConstants util = new GoodsConstants();
        // 循环Excel行数,从第二行开始。
        for (int r = 1; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            goods = new PosterImportGoods();
            // 循环Excel的列
            Integer posterId = null;
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 判断是否为空
                    boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                    if (isNull) {
                        throw new Exception("填写的数据含有空值");
                    }
                    // 判断是否全部是数字
                    if (c == 0) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        for (Map<String, Object> item : list) {
                            if (map.get(item.get("posterId").toString()) == null) {
                                map.put(item.get("posterId").toString(), item.get("posterId"));
                            }
                        }
                        //判断posterId是否在区域维表中存在
                        if (map.get(cell.getStringCellValue().trim()) == null) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "本档期id在区域维表不存在");
                        }
                        try {
                            util.isNumber(cell.getStringCellValue().trim());
                        } catch (Exception e) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列有非数字的数据");
                        }
                        goods.setPosterId(Integer.parseInt(cell.getStringCellValue().trim()));
                        posterId = goods.getPosterId();
                    } else if (c == 1) {
                        try {
                            new PosterImportUtil().stringToDate(cell.getStringCellValue().trim());
                            goods.setStartDate(cell.getStringCellValue().trim());
                        } catch (Exception e) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "开始时间格式不正确");
                        }
                    } else if (c == 2) {
                        try {
                            new PosterImportUtil().stringToDate(cell.getStringCellValue().trim());
                            goods.setEndDate(cell.getStringCellValue().trim());
                        } catch (Exception e) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "结束时间格式不正确");
                        }
                    } else if (c == 3) {
                        goods.setTheme(cell.getStringCellValue().trim());
                    } else if (c == 4) {
                        if (util.queryGroupId(cell.getStringCellValue().trim())) {
                            goods.setGroupId(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 5) {
                        if (util.queryGroupName(cell.getStringCellValue().trim())) {
                            goods.setGroupName(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 6) {
                        if (util.queryDmId(cell.getStringCellValue().trim())) {
                            goods.setDmId(Integer.parseInt(cell.getStringCellValue().trim()));
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 7) {
                        if (util.queryDmName(cell.getStringCellValue().trim())) {
                            goods.setDmName(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 8) {
                        goods.setDisplayMode(cell.getStringCellValue().trim());
                    } else if (c == 9) {
                        if (util.queryGoodsAttribute(cell.getStringCellValue().trim())) {
                            goods.setGoodsAttribute(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 10) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        for (Map<String, Object> item : list) {
                            if (map.get(item.get("posterId") + "-" + item.get("area")) == null) {
                                map.put(item.get("posterId") + "-" + item.get("area"), item);
                            }
                        }
                        if (map.get(posterId + "-" + cell.getStringCellValue().trim()) == null) {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "本档期该区域不存在");
                        }
                        goods.setArea(cell.getStringCellValue().trim());
                    } else if (c == 11) {
                        goods.setGoodsId(cell.getStringCellValue().trim());
                    } else if (c == 12) {
                        goods.setBarcode(cell.getStringCellValue().trim());
                    } else if (c == 13) {
                        goods.setName(cell.getStringCellValue().trim());
                    } else if (c == 14) {
                        goods.setUnit(cell.getStringCellValue().trim());
                    } else if (c == 15) {
                        goods.setInPrice(cell.getStringCellValue().trim());
                    } else if (c == 16) {
                        goods.setPromotionInPrice(cell.getStringCellValue().trim());
                    } else if (c == 17) {
                        goods.setSalePrice(cell.getStringCellValue().trim());
                    } else if (c == 18) {
                        goods.setPromotionSalePrice(cell.getStringCellValue().trim());
                    } else if (c == 19) {
                        goods.setGrossMargin(cell.getStringCellValue().trim());
                    } else if (c == 20) {
                        goods.setPromotionGrossMargin(cell.getStringCellValue().trim());
                    } else if (c == 21) {
                        goods.setEstimatedSaleNumber(cell.getStringCellValue().trim());
                    } else if (c == 22) {
                        goods.setEstimatedSaleAmount(cell.getStringCellValue().trim());
                    } else if (c == 23) {
                        goods.setPosterFee(cell.getStringCellValue().trim());
                    } else if (c == 24) {
                        int i = posterImportService.queryCountry(cell.getStringCellValue().trim());
                        if (i == 1) {
                            goods.setCountry(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("填写的数据含有空值！！" + "第" + r + "行" + (c + 1) + "供应国不存在");
                        }
                    } else if (c == 25) {
                        if (util.queryPriceMethod(cell.getStringCellValue().trim())) {
                            goods.setPriceMethod(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 26) {
                        goods.setSupplieRating(cell.getStringCellValue().trim());
                    } else if (c == 27) {
                        if (util.queryDeliveryMethod(cell.getStringCellValue().trim())) {
                            goods.setDeliveryMethod(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 28) {
                        if (util.queryReturnFlag(cell.getStringCellValue().trim())) {
                            goods.setReturnFlag(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("表格中有非数字的数据！！" + "第" + r + "行" + (c + 1) + "列,数据与模板不符");
                        }
                    } else if (c == 29) {
                        int i = posterImportService.queryVender(cell.getStringCellValue().trim());
                        if (i == 1) {
                            goods.setSupplierCode(cell.getStringCellValue().trim());
                        } else {
                            throw new Exception("填写的数据含有空值！！" + "第" + r + "行" + (c + 1) + "供应商编码不存在");
                        }

                    } else if (c == 30) {
                        goods.setSupplierName(cell.getStringCellValue().trim());
                    }
                } else {
                    throw new Exception("填写的数据含有空值！！" + "第" + r + "行" + (c + 1) + "列有空值");
                }
            }
            // 添加数据
            goodsList.add(goods);
        }
        return goodsList;
    }

    public Date stringToDate(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//小写的mm表示的是分钟
        return sdf.parse(dateStr);
    }
}
