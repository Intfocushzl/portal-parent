package com.yonghui.portal.controller.sys;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysVisitLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-07-24 18:01:46
 */
@RestController
@RequestMapping("sysoperationlog")
public class SysOperationLogController extends AbstractController {
    @Autowired
    private SysVisitLogService sysVisitLogService;

    /**
     * 近30天访问日志图表
     */
    @RequestMapping("/visit")
    @RequiresPermissions("sysoperationlog:visit")
    public R visit(@RequestParam Map<String, Object> params) {

        Date endNext = new DateTime().plusDays(1).withMillisOfDay(0).toDate();
        Date begin = new DateTime(endNext.getTime()).minusDays(30).toDate();
        params.put("endNext", endNext);
        params.put("begin", begin);

        List<Map<String, Object>> visit = sysVisitLogService.queryVisit(params);

        return R.success().put("visit", visit);
    }

    /**
     * 当日访各时段问日志图表
     */
    @RequestMapping("/visitData")
    @RequiresPermissions("sysoperationlog:visit")
    public R visitData(@RequestParam Map<String, Object> params) {

        Date endNext = new DateTime().plusDays(1).withMillisOfDay(0).toDate();
        Date begin = new DateTime().withMillisOfDay(0).toDate();
        params.put("endNext", endNext);
        params.put("begin", begin);

        List<Map<String, Object>> visitData = sysVisitLogService.queryVisitByData(params);

        return R.success().put("visitData", visitData);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysoperationlog:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<SysOperationLog> sysOperationLogList = sysVisitLogService.queryList(query);
        int total = sysVisitLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysOperationLogList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysoperationlog:info")
    public R info(@PathVariable("id") Integer id) {
        SysOperationLog sysOperationLog = sysVisitLogService.queryObject(id);
        return R.success().put("sysOperationLog", sysOperationLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysoperationlog:save")
    public R save(@RequestBody SysOperationLog sysOperationLog) {
        sysVisitLogService.save(sysOperationLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysoperationlog:update")
    public R update(@RequestBody SysOperationLog sysOperationLog) {
        sysVisitLogService.update(sysOperationLog);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysoperationlog:delete")
    public R delete(@RequestBody Integer[] ids) {
        sysVisitLogService.deleteBatch(ids);
        return R.success();
    }
}
