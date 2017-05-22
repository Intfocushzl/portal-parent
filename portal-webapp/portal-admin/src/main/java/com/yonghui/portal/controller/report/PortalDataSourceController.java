package com.yonghui.portal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.service.report.PortalDataSourceService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import com.yonghui.portal.utils.redis.RedisBizUtilAdmin;
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
@RequestMapping("portaldatasource")
public class PortalDataSourceController extends AbstractController {
    @Autowired
    private PortalDataSourceService portalDataSourceService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("portaldatasource:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<PortalDataSource> portalDataSourceList = portalDataSourceService.queryList(query);
        int total = portalDataSourceService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(portalDataSourceList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{code}")
    @RequiresPermissions("portaldatasource:info")
    public R info(@PathVariable("code") String code){
        PortalDataSource portalDataSource = portalDataSourceService.queryObjectByCode(code);
        return R.success().put("portalDataSource", portalDataSource);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("portaldatasource:save")
    public R save(@RequestBody PortalDataSource portalDataSource){
		portalDataSourceService.save(portalDataSource);
        redisBizUtilAdmin.setPortalDataSource(portalDataSource.getCodeOld(), portalDataSource.getCode(), JSONObject.toJSONString(portalDataSource));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("portaldatasource:update")
    public R update(@RequestBody PortalDataSource portalDataSource){
		portalDataSourceService.update(portalDataSource);
        redisBizUtilAdmin.setPortalDataSource(portalDataSource.getCodeOld(), portalDataSource.getCode(), JSONObject.toJSONString(portalDataSource));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("portaldatasource:delete")
    public R delete(@RequestBody String[] codes){
        portalDataSourceService.deleteBatchByCodes(codes);
        for (String c:codes) {
            redisBizUtilAdmin.removePortalDataSource(c);
        }
        return R.success();
    }

}
