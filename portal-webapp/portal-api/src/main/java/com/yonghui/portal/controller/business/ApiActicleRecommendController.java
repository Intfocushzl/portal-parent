package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.service.business.ApiActicleRecommendService;
import com.yonghui.portal.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 推荐文章查询
 *
 * @author liuwei
 * @date 2017-07-06
 */

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business", description = "推荐文章查询")
public class ApiActicleRecommendController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private ApiActicleRecommendService apiActicleRecommendService;

    /**
     * 热门课程
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    @ApiOperation(value = "热门课程查询", produces = MediaType.APPLICATION_JSON_VALUE, notes = "查询热门课程", response = R.class)
    public R list(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = null;
        try {
            list = apiActicleRecommendService.queryRecommend();
        } catch (Exception e) {
            return R.error("热门课程查询失败!!!" + e.getMessage());
        }
        return R.success(list);
    }
}
