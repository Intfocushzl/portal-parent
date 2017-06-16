package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanNoticeLog;
import com.yonghui.portal.service.businessman.BusinessmanNoticeLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 生意人公告信息阅读日志
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
@RestController
@RequestMapping("businessmannoticelog")
public class BusinessmanNoticeLogController extends AbstractController {
    @Autowired
    private BusinessmanNoticeLogService businessmanNoticeLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmannoticelog:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanNoticeLog> businessmanNoticeLogList = businessmanNoticeLogService.queryList(query);
        int total = businessmanNoticeLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanNoticeLogList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmannoticelog:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanNoticeLog businessmanNoticeLog = businessmanNoticeLogService.queryObject(id);
        return R.success().put("businessmanNoticeLog", businessmanNoticeLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmannoticelog:save")
    public R save(@RequestBody BusinessmanNoticeLog businessmanNoticeLog){
		businessmanNoticeLogService.save(businessmanNoticeLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmannoticelog:update")
    public R update(@RequestBody BusinessmanNoticeLog businessmanNoticeLog){
		businessmanNoticeLogService.update(businessmanNoticeLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmannoticelog:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanNoticeLogService.deleteBatch(ids);
        return R.success();
    }

}
