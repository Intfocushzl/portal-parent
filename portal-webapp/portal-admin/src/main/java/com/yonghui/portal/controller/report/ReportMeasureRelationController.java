package com.yonghui.portal.controller.report;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.ReportMeasureRelation;
import com.yonghui.portal.service.report.ReportMeasureRelationService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.RRException;
import com.yonghui.portal.util.report.columns.MultipleTree;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
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
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

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
     *
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportmeasurerelation:save")
    public R save(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.save(reportMeasureRelation);
        opRedisColumns(reportMeasureRelation.getReportcode());
        return R.success();
    }

    /**
     * 修改
     *
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportmeasurerelation:update")
    public R update(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.update(reportMeasureRelation);
        opRedisColumns(reportMeasureRelation.getReportcode());
        return R.success();
    }

    /**
     * 删除
     *
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportmeasurerelation:delete")
    public R delete(@RequestBody Long[] ids, String reportcode) {
        reportMeasureRelationService.deleteBatch(ids);
        opRedisColumns(reportcode);
        return R.success();
    }

    /**
     * redis 的表头信息操作
     *
     * @param reportcode
     */
    public void opRedisColumns(String reportcode) {
        if (reportcode != null) {
            List<ReportMeasureRelation> relations = reportMeasureRelationService.queryListByReportCode(reportcode);
            if (relations != null && relations.size() > 0) {
                try {
                    // 保存或修改
                    String jsonString = MultipleTree.getTreeJsonString(relations);
                    redisBizUtilAdmin.setReportColumns(reportcode, jsonString);
                } catch (Exception e) {
                    logger.error("生产多叉树失败");
                    e.printStackTrace();
                }
            } else {
                // 删除
                redisBizUtilAdmin.removeReportColumns(reportcode);
            }
        } else {
            throw new RRException("未获取到报表编码");
        }
    }
}
