package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.service.ApiService;
import com.yonghui.portal.util.ApiExportExport;
import com.yonghui.portal.util.HttpContextUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.xss.SQLFilter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 报表存错过程报表统一入口
 * 张海 2017.05.11
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private ApiService apiService;

    /**
     * 报表存储过程报表统一入口
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode 报表编码,字段名唯一，且不允许修改
     * @return
     */
    @IgnoreAuth
    @RequestMapping(value = "portal/custom")
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        String parameter = HttpContextUtils.getRequestParameter(req);
        if (StringUtils.isEmpty(yongHuiReportCustomCode)) {
            return R.error("查询编码不能为空");
        }
        List<Map<String, Object>> dataList = apiService.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
        return R.success(dataList);
    }

    /**
     * 导出excel统一入口
     * 测试中,待完善
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode
     */
    @IgnoreAuth
    @RequestMapping(value = "exportExcel")
    public void exportExcel(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        try {
            String parameter = HttpContextUtils.getRequestParameter(req);
            if (!StringUtils.isEmpty(yongHuiReportCustomCode)) {
                List<Map<String, Object>> dataList = apiService.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
                ApiExportExport export = new ApiExportExport();
                //测试
                String[] cellTitleName = {"sdate:日期", "flag:列名1", "abc:列名2", "title:说明", "sname:店名"};
                HSSFWorkbook workbook = export.export(dataList, cellTitleName, "YongHui-数据");
                String filename = "YH-DATA.xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            log.error("导出excel异常", e);
        }
    }

}
