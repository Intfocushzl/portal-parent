package com.yonghui.portal.controller.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.sys.SysStartLog;
import com.yonghui.portal.service.TestApiService;
import com.yonghui.portal.service.TestDataSourceService;
import com.yonghui.portal.service.TestSysStartLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台controller
 * <p>
 * Created by 张海 on 2017/04/27.
 */
@Component
@Api(value = "test", description = "接口测试/数据源测试")
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private Logger log = Logger.getLogger(this.getClass());

    // 使用dubbo注解调用服务
    @Reference
    private TestApiService testApiService;
    // 使用dubbo注解调用服务
    @Reference
    private TestDataSourceService testDataSourceService;
    // 使用dubbo注解调用服务
    @Reference
    private TestSysStartLogService testSysStartLogService;

    /**
     * 接口测试
     * @IgnoreAuth 忽略Token验证测试
     * @param modelMap
     * @return
     */
    @IgnoreAuth
    @ApiOperation(value = "hello")
    @RequestMapping(value = "/hello")
    public R hello(ModelMap modelMap) {
        String str = testApiService.hello("world");
        return R.success(str);
    }

    /**
     * 数据源b测试
     * @IgnoreAuth 忽略Token验证测试
     * @param modelMap
     * @return
     */
    @IgnoreAuth
    @ApiOperation(value = "datab")
    @RequestMapping(value = "/datab")
    public R datab(ModelMap modelMap) {
        testDataSourceService.dataB();
        return R.success().put("aaa", "新添加了自定义属性aaa,我是属性值");
    }

    /**
     * 数据源c测试
     *
     * @param modelMap
     * @return
     */
    @ApiOperation(value = "datac")
    @RequestMapping(value = "/datac")
    public R datac(ModelMap modelMap) {
        testDataSourceService.dataC();
        return R.success();
    }

    /**
     * 返回查询列表
     * http://127.0.0.1:8080/test/list?page=1&limit=10
     */
    @RequestMapping("/list")
    public R list(Integer page, Integer limit) {
        // 封装查询对象
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        // 查询列表数据
        List<SysStartLog> sysStartLogList = testSysStartLogService.queryList(map);
        // 总记录数
        int total = testSysStartLogService.queryTotal(map);
        // 分页对象
        PageUtils pageUtil = new PageUtils(sysStartLogList, total, limit, page);

        return R.success(pageUtil);
    }

    /**
     * 返回对象信息
     * http://127.0.0.1:8080/test/info/331
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SysStartLog sysStartLog = testSysStartLogService.queryObject(id);

        return R.success(sysStartLog);
    }
}