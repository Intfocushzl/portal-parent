package com.yonghui.portal.controller.report;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.api.PortalExecuteSql;
import com.yonghui.portal.service.PortalExecuteSqlService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.Query;
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
 * @date 2017-05-17 16:35:37
 */
@RestController
@RequestMapping("portalexecutesql")
public class PortalExecuteSqlController extends AbstractController {
    @Autowired
    private PortalExecuteSqlService portalExecuteSqlService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalexecutesql:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<PortalExecuteSql> portalExecuteSqlList = portalExecuteSqlService.queryList(query);
        int total = portalExecuteSqlService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalExecuteSqlList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("portalexecutesql:info")
    public R info(@PathVariable("id") Integer id){
        PortalExecuteSql portalExecuteSql = portalExecuteSqlService.queryObject(id);
        return R.success().put("portalExecuteSql", portalExecuteSql);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalexecutesql:save")
    public R save(@RequestBody PortalExecuteSql portalExecuteSql){
		portalExecuteSqlService.save(portalExecuteSql);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalexecutesql:update")
    public R update(@RequestBody PortalExecuteSql portalExecuteSql){
		portalExecuteSqlService.update(portalExecuteSql);
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalexecutesql:delete")
    public R delete(@RequestBody Integer[] ids){
		portalExecuteSqlService.deleteBatch(ids);
        return R.success();
    }

}
