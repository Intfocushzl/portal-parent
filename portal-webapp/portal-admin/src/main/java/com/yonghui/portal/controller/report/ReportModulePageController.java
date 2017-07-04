package com.yonghui.portal.controller.report;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.ReportModulePage;
import com.yonghui.portal.service.report.ReportModulePageService;
import com.yonghui.portal.util.GzipUtils;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 报表专题信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-16 11:23:25
 */
@RestController
@RequestMapping("reportmodulepage")
public class ReportModulePageController extends AbstractController {
    @Autowired
    private ReportModulePageService reportModulePageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportmodulepage:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ReportModulePage> reportModulePageList = reportModulePageService.queryList(query);
        int total = reportModulePageService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(reportModulePageList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportmodulepage:info")
    public R info(@PathVariable("id") Long id) {
        ReportModulePage reportModulePage = reportModulePageService.queryObject(id);
        return R.success().put("reportModulePage", reportModulePage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportmodulepage:save")
    public R save(@RequestBody ReportModulePage reportModulePage) {
        reportModulePage.setDesignStructrue(GzipUtils.gzip(reportModulePage.getContent()));
        if (reportModulePage.getStatus() == 2) {
            reportModulePage.setOnlineStructrue(reportModulePage.getDesignStructrue());
        }
        reportModulePageService.save(reportModulePage);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportmodulepage:update")
    public R update(@RequestBody ReportModulePage reportModulePage) {
        reportModulePage.setDesignStructrue(GzipUtils.gzip(reportModulePage.getContent()));
        if (reportModulePage.getStatus() == 2) {
            reportModulePage.setOnlineStructrue(reportModulePage.getDesignStructrue());
        }
        reportModulePageService.update(reportModulePage);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportmodulepage:delete")
    public R delete(@RequestBody Long[] ids) {
        reportModulePageService.deleteBatch(ids);
        return R.success();
    }

}
