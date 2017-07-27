package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalRouteReport;
import com.yonghui.portal.service.report.PortalRouteReportService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:43:38
 */
@RestController
@RequestMapping("portalroutereport")
public class PortalRouteReportController extends AbstractController {
    @Autowired
    private PortalRouteReportService portalRouteReportService;

    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalroutereport:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<PortalRouteReport> portalRouteReportList = portalRouteReportService.queryList(query);
        int total = portalRouteReportService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalRouteReportList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 报表配置 查询列表
     * @return
     */
    @RequestMapping("/provideList")
    @ResponseBody
    public R provideList() {
        Map<String, Object> map = new HashMap<>();
        List<PortalRouteReport> provideList = portalRouteReportService.queryList(map);
        return R.success().put("provideList", provideList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("portalroutereport:info")
    public R info(@PathVariable("id") Integer id) {
        PortalRouteReport portalRouteReport = portalRouteReportService.queryObject(id);
        return R.success().put("portalRouteReport", portalRouteReport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalroutereport:save")
    public R save(@RequestBody PortalRouteReport portalRouteReport) {
        portalRouteReportService.save(portalRouteReport);
        redisBizUtilAdmin.setRouteReport(portalRouteReport.getCodeOld(), portalRouteReport.getCode(), JSONObject.toJSONString(portalRouteReport));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalroutereport:update")
    public R update(@RequestBody PortalRouteReport portalRouteReport) {
        portalRouteReportService.update(portalRouteReport);
        redisBizUtilAdmin.setRouteReport(portalRouteReport.getCodeOld(), portalRouteReport.getCode(), JSONObject.toJSONString(portalRouteReport));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalroutereport:delete")
    public R delete(@RequestBody String[] codes) {
        portalRouteReportService.deleteBatch(codes);
        for (String c : codes) {
            redisBizUtilAdmin.removeRouteReport(c);
        }
        return R.success();
    }


    //一键缓存
    @RequestMapping("/addRedis")
    public R addRedis(@RequestBody String[] codes){
        for (String code:codes) {
            PortalRouteReport portalRouteReport = portalRouteReportService.queryObjectByCode(code);
            redisBizUtilAdmin.setRouteReport(code, code, JSONObject.toJSONString(portalRouteReport));
        }
        return R.success();
    }

    /**
     * 产生新的编码
     */
    @RequestMapping("/getNewMaxCode")
    public R getNewMaxCode() {
        return R.success().put("newMaxCode", portalRouteReportService.getNewMaxCode());
    }
}
