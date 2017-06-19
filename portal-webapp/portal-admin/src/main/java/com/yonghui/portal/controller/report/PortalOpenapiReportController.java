package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalOpenapiReport;
import com.yonghui.portal.service.report.PortalOpenapiReportService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 14:01:50
 */
@RestController
@RequestMapping("portalopenapireport")
public class PortalOpenapiReportController extends AbstractController {

    @Autowired
    private PortalOpenapiReportService portalOpenapiReportService;

    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalopenapireport:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<PortalOpenapiReport> portalOpenapiReportList = portalOpenapiReportService.queryList(query);
        int total = portalOpenapiReportService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalOpenapiReportList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("portalopenapireport:info")
    public R info(@PathVariable("id") Integer id) {
        PortalOpenapiReport portalOpenapiReport = portalOpenapiReportService.queryObject(id);
        return R.success().put("portalOpenapiReport", portalOpenapiReport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalopenapireport:save")
    public R save(@RequestBody PortalOpenapiReport portalOpenapiReport) {
        portalOpenapiReportService.save(portalOpenapiReport);
        redisBizUtilAdmin.setOpenApiReport(portalOpenapiReport.getCodeOld(), portalOpenapiReport.getCode(), JSONObject.toJSONString(portalOpenapiReport));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalopenapireport:update")
    public R update(@RequestBody PortalOpenapiReport portalOpenapiReport) {
        portalOpenapiReportService.update(portalOpenapiReport);
        redisBizUtilAdmin.setOpenApiReport(portalOpenapiReport.getCodeOld(), portalOpenapiReport.getCode(), JSONObject.toJSONString(portalOpenapiReport));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalopenapireport:delete")
    public R delete(@RequestBody String[] codes) {
        portalOpenapiReportService.deleteBatch(codes);
        for (String c : codes) {
            redisBizUtilAdmin.removeRouteReport(c);
        }
        return R.success();
    }

}
