package com.yonghui.portal.controller.report;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.ReportMeasureRelation;
import com.yonghui.portal.service.report.ReportMeasureRelationService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-22 16:17:07
 */
@RestController
@RequestMapping("reportmeasurerelation")
public class ReportMeasureRelationController extends AbstractController {
    @Autowired
    private ReportMeasureRelationService reportMeasureRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportmeasurerelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ReportMeasureRelation> reportMeasureRelationList = reportMeasureRelationService.queryList(query);
        int total = reportMeasureRelationService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(reportMeasureRelationList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportmeasurerelation:info")
    public R info(@PathVariable("id") Long id) {
        ReportMeasureRelation reportMeasureRelation = reportMeasureRelationService.queryObject(id);
        return R.success().put("reportMeasureRelation", reportMeasureRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportmeasurerelation:save")
    public R save(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.save(reportMeasureRelation);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportmeasurerelation:update")
    public R update(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.update(reportMeasureRelation);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportmeasurerelation:delete")
    public R delete(@RequestBody Long[] ids) {
        reportMeasureRelationService.deleteBatch(ids);
        return R.success();
    }

}
