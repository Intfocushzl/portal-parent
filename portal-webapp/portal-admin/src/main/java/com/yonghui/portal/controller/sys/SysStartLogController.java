package com.yonghui.portal.controller.sys;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonghui.portal.model.sys.SysStartLog;
import com.yonghui.portal.service.SysStartLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;

/**
 * 系统启动日志记录
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:32:54
 */
@RestController
@RequestMapping("sysstartlog")
public class SysStartLogController extends AbstractController {
    @Autowired
    private SysStartLogService sysStartLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysstartlog:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<SysStartLog> sysStartLogList = sysStartLogService.queryList(query);
        int total = sysStartLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysStartLogList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysstartlog:info")
    public R info(@PathVariable("id") Long id){
        SysStartLog sysStartLog = sysStartLogService.queryObject(id);
        return R.success().put("sysStartLog", sysStartLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysstartlog:save")
    public R save(@RequestBody SysStartLog sysStartLog){
		sysStartLogService.save(sysStartLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysstartlog:update")
    public R update(@RequestBody SysStartLog sysStartLog){
		sysStartLogService.update(sysStartLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysstartlog:delete")
    public R delete(@RequestBody Long[] ids){
		sysStartLogService.deleteBatch(ids);
        return R.success();
    }

}
