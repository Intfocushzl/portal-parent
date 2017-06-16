package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalReport;
import com.yonghui.portal.service.report.PortalReportService;
import com.yonghui.portal.util.GzipUtils;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.ShiroUtils;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-18 13:05:16
 */
@RestController
@RequestMapping("portalreport")
public class PortalReportController extends AbstractController {
    @Autowired
    private PortalReportService portalReportService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalreport:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<PortalReport> portalReportList = portalReportService.queryList(query);
        int total = portalReportService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalReportList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{code}")
    @RequiresPermissions("portalreport:info")
    public R info(@PathVariable("code") String code) {
        PortalReport portalReport = portalReportService.queryObjectByCode(code);
        return R.success().put("portalReport", portalReport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalreport:save")
    public R save(@RequestBody PortalReport portalReport) {
        portalReport.setCreater(ShiroUtils.getUserId());

        portalReportService.save(portalReport);
        portalReport.setReportHotData(GzipUtils.gzip(portalReport.getReportHotData()));
        portalReport.setReportHeadersFormatConsole(GzipUtils.gzip(portalReport.getReportHeadersFormatConsole()));
        portalReport.setReportOuterHtml(GzipUtils.gzip(portalReport.getReportOuterHtml()));
        redisBizUtilAdmin.setPortalReport(portalReport.getCodeOld(), portalReport.getCode(), JSONObject.toJSONString(portalReport));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalreport:update")
    public R update(@RequestBody PortalReport portalReport) {
        portalReport.setCreater(ShiroUtils.getUserId());

        portalReportService.update(portalReport);
        portalReport.setReportHotData(GzipUtils.gzip(portalReport.getReportHotData()));
        portalReport.setReportHeadersFormatConsole(GzipUtils.gzip(portalReport.getReportHeadersFormatConsole()));
        portalReport.setReportOuterHtml(GzipUtils.gzip(portalReport.getReportOuterHtml()));
        redisBizUtilAdmin.setPortalReport(portalReport.getCodeOld(), portalReport.getCode(), JSONObject.toJSONString(portalReport));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalreport:delete")
    public R delete(@RequestBody String[] codes) {
        portalReportService.deleteBatchByCodes(codes);
        for (String c : codes) {
            redisBizUtilAdmin.removePortalReport(c);
        }
        return R.success();
    }

    @RequestMapping("/addRedis")
    public R addRedis(@RequestBody String[] codes) {
        for (String code : codes) {
            PortalReport portalReport = portalReportService.queryObjectByCode(code);
            redisBizUtilAdmin.setPortalReport(code, code, JSONObject.toJSONString(portalReport));
        }
        return R.success();
    }

}
