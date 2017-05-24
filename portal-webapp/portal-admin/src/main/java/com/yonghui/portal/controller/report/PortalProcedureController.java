package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.service.report.PortalProcedureService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
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
 * @date 2017-05-17 18:15:12
 */
@RestController
@RequestMapping("portalprocedure")
public class PortalProcedureController extends AbstractController {
    @Autowired
    private PortalProcedureService portalProcedureService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portalprocedure:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<PortalProcedure> portalProcedureList = portalProcedureService.queryList(query);
        int total = portalProcedureService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalProcedureList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{procode}")
    @RequiresPermissions("portalprocedure:info")
    public R info(@PathVariable("procode") String procode) {
        PortalProcedure portalProcedure = portalProcedureService.queryObjectByProcode(procode);
        return R.success().put("portalProcedure", portalProcedure);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portalprocedure:save")
    public R save(@RequestBody PortalProcedure portalProcedure) {
        portalProcedureService.save(portalProcedure);
        redisBizUtilAdmin.setPortalProcedure(portalProcedure.getProcodeOld(), portalProcedure.getProcode(), JSONObject.toJSONString(portalProcedure));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portalprocedure:update")
    public R update(@RequestBody PortalProcedure portalProcedure) {
        portalProcedureService.update(portalProcedure);
        redisBizUtilAdmin.setPortalProcedure(portalProcedure.getProcodeOld(), portalProcedure.getProcode(), JSONObject.toJSONString(portalProcedure));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portalprocedure:delete")
    public R delete(@RequestBody String[] procodes) {
        portalProcedureService.deleteBatch(procodes);
        for (String procode : procodes) {
            redisBizUtilAdmin.removePortalProcedure(procode);
        }
        return R.success();
    }

    //report配置中查询procode
    @RequestMapping("/proList")
    @ResponseBody
    public R proList() {
        Map<String,Object> map = new HashMap<>();
        List<PortalProcedure> portalExecuteProList = portalProcedureService.queryList(map);
        return R.success().put("proList", portalExecuteProList);
    }

}
