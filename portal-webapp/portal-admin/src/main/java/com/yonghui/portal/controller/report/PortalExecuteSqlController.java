package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalExecuteSql;
import com.yonghui.portal.service.report.PortalExecuteSqlService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.ShiroUtils;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-17 16:35:37
 */
@RestController
@RequestMapping("portalexecutesql")
public class PortalExecuteSqlController extends AbstractController {
    @Autowired
    private PortalExecuteSqlService portalExecuteSqlService;

    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalexecutesql:list")
    public R list(@RequestParam Map<String, Object> params) {
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
    @RequestMapping("/info/{sqlcode}")
    @RequiresPermissions("portalexecutesql:info")
    public R info(@PathVariable("sqlcode") String sqlcode) {
        PortalExecuteSql portalExecuteSql = portalExecuteSqlService.queryObjectBySqlcode(sqlcode);
        return R.success().put("portalExecuteSql", portalExecuteSql);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalexecutesql:save")
    public R save(@RequestBody PortalExecuteSql portalExecuteSql) {
        portalExecuteSql.setCreater(ShiroUtils.getUserId());
        portalExecuteSqlService.save(portalExecuteSql);
        redisBizUtilAdmin.setPortalExecuteSql(portalExecuteSql.getSqlcodeOld(), portalExecuteSql.getSqlcode(), JSONObject.toJSONString(portalExecuteSql));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalexecutesql:update")
    public R update(@RequestBody PortalExecuteSql portalExecuteSql) {
        portalExecuteSql.setCreater(ShiroUtils.getUserId());
        portalExecuteSqlService.update(portalExecuteSql);
        redisBizUtilAdmin.setPortalExecuteSql(portalExecuteSql.getSqlcodeOld(), portalExecuteSql.getSqlcode(), JSONObject.toJSONString(portalExecuteSql));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalexecutesql:delete")
    public R delete(@RequestBody String[] sqlcodes) {
        portalExecuteSqlService.deleteBatchBySqlcodes(sqlcodes);
        for (String sqlcode : sqlcodes) {
            redisBizUtilAdmin.removePortalExecuteSql(sqlcode);
        }
        return R.success();
    }

    //report配置中查询sqlcode
    @RequestMapping("/sqlList")
    @ResponseBody
    public R sqlList() {
        Map<String,Object> map = new HashMap<>();
        List<PortalExecuteSql> portalExecuteSqlList = portalExecuteSqlService.queryList(map);
        return R.success().put("sqlList", portalExecuteSqlList);
    }

}
