package com.yonghui.portal.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.sys.SysFtpConfig;
import com.yonghui.portal.service.sys.SysFtpConfigService;
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
 * 系统FTP配置信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-09 11:39:14
 */
@RestController
@RequestMapping("/sys/sysftpconfig")
public class SysFtpConfigController extends AbstractController {
    @Autowired
    private SysFtpConfigService sysFtpConfigService;
    @Autowired
    private RedisBizUtilAdmin redisBizUtilAdmin;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysftpconfig:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<SysFtpConfig> sysFtpConfigList = sysFtpConfigService.queryList(query);
        int total = sysFtpConfigService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysFtpConfigList, total, query.getLimit(), query.getPage());

        return R.success().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysftpconfig:info")
    public R info(@PathVariable("id") Long id) {
        SysFtpConfig sysFtpConfig = sysFtpConfigService.queryObject(id);
        return R.success().put("sysFtpConfig", sysFtpConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysftpconfig:save")
    public R save(@RequestBody SysFtpConfig sysFtpConfig) {
        sysFtpConfigService.save(sysFtpConfig);
        redisBizUtilAdmin.setFtpInfo(sysFtpConfig.getId(), JSONObject.toJSONString(sysFtpConfig));
        return R.success();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysftpconfig:update")
    public R update(@RequestBody SysFtpConfig sysFtpConfig) {
        sysFtpConfigService.update(sysFtpConfig);
        redisBizUtilAdmin.setFtpInfo(sysFtpConfig.getId(), JSONObject.toJSONString(sysFtpConfig));
        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysftpconfig:delete")
    public R delete(@RequestBody Long[] ids) {
        sysFtpConfigService.deleteBatch(ids);

        for (Long id : ids) {
            redisBizUtilAdmin.removeFtpInfo(id);
        }
        return R.success();
    }

}
