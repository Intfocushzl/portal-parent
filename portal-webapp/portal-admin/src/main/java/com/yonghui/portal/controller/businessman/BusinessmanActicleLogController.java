package com.yonghui.portal.controller.businessman;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.businessman.BusinessmanActicleLog;
import com.yonghui.portal.service.businessman.BusinessmanActicleLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 生意人文章信息阅读日志
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
@RestController
@RequestMapping("businessmanacticlelog")
public class BusinessmanActicleLogController extends AbstractController {
    @Autowired
    private BusinessmanActicleLogService businessmanActicleLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("businessmanacticlelog:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<BusinessmanActicleLog> businessmanActicleLogList = businessmanActicleLogService.queryList(query);
        int total = businessmanActicleLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(businessmanActicleLogList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("businessmanacticlelog:info")
    public R info(@PathVariable("id") Long id){
        BusinessmanActicleLog businessmanActicleLog = businessmanActicleLogService.queryObject(id);
        return R.success().put("businessmanActicleLog", businessmanActicleLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("businessmanacticlelog:save")
    public R save(@RequestBody BusinessmanActicleLog businessmanActicleLog){
		businessmanActicleLogService.save(businessmanActicleLog);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("businessmanacticlelog:update")
    public R update(@RequestBody BusinessmanActicleLog businessmanActicleLog){
		businessmanActicleLogService.update(businessmanActicleLog);
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("businessmanacticlelog:delete")
    public R delete(@RequestBody Long[] ids){
		businessmanActicleLogService.deleteBatch(ids);
        return R.success();
    }

    @RequestMapping("getListByArticleId")
    public R getListByArticleId(@RequestParam Map<String, Object> params){
        Integer id = Integer.parseInt(params.get("id").toString());
        List<BusinessmanActicleLog> list = businessmanActicleLogService.getListByArticleId(id);
        return R.success().setData(list);
    }

}
