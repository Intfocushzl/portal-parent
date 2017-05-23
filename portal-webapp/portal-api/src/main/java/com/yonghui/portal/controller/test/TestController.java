package com.yonghui.portal.controller.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.service.test.TestDataSourceService;
import com.yonghui.portal.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TestDataSourceService testDataSourceService;

    /**
     * 数据源b测试
     *
     * @param modelMap
     * @return
     * @IgnoreAuth 忽略Token验证测试
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

}