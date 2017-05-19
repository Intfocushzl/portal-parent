package com.yonghui.portal.controller.report;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.api.PortalReport;
import com.yonghui.portal.service.PortalReportService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-18 13:05:16
 */
@RestController
@RequestMapping("portalreport")
public class PortalReportController extends AbstractController {
    @Autowired
    private PortalReportService portalReportService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalreport:list")
    public R list(@RequestParam Map<String, Object> params){
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
    @RequestMapping("/info/{id}")
    @RequiresPermissions("portalreport:info")
    public R info(@PathVariable("id") Integer id){
        PortalReport portalReport = portalReportService.queryObject(id);
        return R.success().put("portalReport", portalReport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalreport:save")
    public R save(@RequestBody PortalReport portalReport){
		portalReportService.save(portalReport);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalreport:update")
    public R update(@RequestBody PortalReport portalReport){
		portalReportService.update(portalReport);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalreport:delete")
    public R delete(@RequestBody Integer[] ids){
		portalReportService.deleteBatch(ids);
        return R.success();
    }

}
