package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.ReportMeasureRelation;
import com.yonghui.portal.service.report.ReportMeasureRelationService;
import com.yonghui.portal.util.ListToTreeUtils;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itextpdf.text.pdf.PdfName.op;

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
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportmeasurerelation:save")
    public R save(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.save(reportMeasureRelation);
        opRedisColumns(reportMeasureRelation);
        return R.success();
    }

    /**
     * 修改
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportmeasurerelation:update")
    public R update(@RequestBody ReportMeasureRelation reportMeasureRelation) {
        reportMeasureRelationService.update(reportMeasureRelation);
        opRedisColumns(reportMeasureRelation);
        return R.success();
    }

    /**
     * 删除
     * 删除
     * @Date 2017-05-24 添加redis操作 shengwm
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportmeasurerelation:delete")
    public R delete(@RequestBody Long[] ids) {
        List<ReportMeasureRelation> list  = new ArrayList<ReportMeasureRelation>();
        for (Long id: ids ) {
            ReportMeasureRelation reportMeasureRelation = reportMeasureRelationService.queryObject(id);
            if (reportMeasureRelation != null && reportMeasureRelation.getReportcode() != null){
                list.add(reportMeasureRelation);
            }
        }
        reportMeasureRelationService.deleteBatch(ids);
        if (list != null && list.size() > 0){
            for (ReportMeasureRelation relation: list) {
                opRedisColumns(relation);
            }
        }
        return R.success();
    }

    //redis 的表头信息操作
    public void opRedisColumns(ReportMeasureRelation reportMeasureRelation) {
        Map<String,Object> map = new HashMap<>();
        if (reportMeasureRelation.getReportcode() != null){
            map.put("reportcode",reportMeasureRelation.getReportcode());
         //   Query query = new Query(map);
            List<ReportMeasureRelation> relations = reportMeasureRelationService.queryList(map);
            if (relations != null && relations.size() > 0){
                List<ReportMeasureRelation> list = new ListToTreeUtils<>().listTreeTableColumns(relations);
                redisBizUtilAdmin.setReportColumns(reportMeasureRelation.getReportcode(), JSONObject.toJSONString(list));
            }
        }
    }
}
