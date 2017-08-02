package com.yonghui.portal.controller.cindex;

import com.yonghui.portal.annotation.SysLog;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.cindex.ReportDimIndex;
import com.yonghui.portal.service.cindex.ReportDimIndexService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
@RestController
@RequestMapping("reportdimindex")
public class ReportDimIndexController extends AbstractController {
    @Autowired
    private ReportDimIndexService reportDimIndexService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportdimindex:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ReportDimIndex> reportDimIndexList = reportDimIndexService.queryList(query);
        int total = reportDimIndexService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(reportDimIndexList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 列表
     */
    @RequestMapping("/listOpt")
    public R listOpt(@RequestParam Map<String, Object> params) {
        List<ReportDimIndex> reportDimIndexList = reportDimIndexService.queryListOpt();
        return R.success(reportDimIndexList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportdimindex:info")
    public R info(@PathVariable("id") Integer id) {
        ReportDimIndex reportDimIndex = reportDimIndexService.queryObject(id);
        return R.success().put("reportDimIndex", reportDimIndex);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportdimindex:save")
    public R save(@RequestBody ReportDimIndex reportDimIndex) {
        reportDimIndex.setCreater(ShiroUtils.getUserId());
        reportDimIndexService.save(reportDimIndex);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportdimindex:update")
    public R update(@RequestBody ReportDimIndex reportDimIndex) {
        reportDimIndex.setModifier(ShiroUtils.getUserId());
        reportDimIndexService.update(reportDimIndex);
        return R.success();
    }

    /**
     * 修改
     */
    @SysLog("删除维度定义")
    @RequestMapping("/delete")
    @RequiresPermissions("reportdimindex:delete")
    public R delete(@RequestBody Integer[] ids) {
        reportDimIndexService.deleteBatch(ids);
        return R.success();
    }

}
