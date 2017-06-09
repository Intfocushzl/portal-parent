package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.service.business.ApiActicleService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.ApiQuery;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文章
 * 张海 2017.06.07  modify by liuwei
 */
@RestController
@RequestMapping("/api/business")
public class ApiActicleController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Reference
    private ApiActicleService apiActicleService;

    /**
     * 获取文章信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "acticle" , method = RequestMethod.GET)
    public R acticle(HttpServletRequest req, HttpServletResponse response , @RequestParam Map<String, Object> params) {
        return R.success();
    }

    /**
     * 获取文章信息列表
     *
     * @param req
     * @param response
     */
    @OpenAuth
    @RequestMapping(value = "acticleList", method = RequestMethod.GET)
    public R acticleList(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        PageUtils pageUtil = null;
        try {
            ApiQuery query = new ApiQuery(params);
            List<Map<String, Object>> acticleList = apiActicleService.acticleList(query);
            int total = apiActicleService.queryTotal(query);
            pageUtil = new PageUtils(acticleList, total, query.getLimit(), query.getPage());
        } catch (Exception e) {
            R.error("获取文章信息失败");
        }
        return R.success().put("page", pageUtil);
    }

    @OpenAuth
    @RequestMapping(value = "acticleDetail", method = RequestMethod.GET)
    public R acticleDetail(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        PageUtils pageUtil = null;
        try {
            ApiQuery query = new ApiQuery(params);
            List<Map<String, Object>> acticleDetail = apiActicleService.acticleDetail(query);
            int total = apiActicleService.queryTotal(query);
            pageUtil = new PageUtils(acticleDetail, total, query.getLimit(), query.getPage());
        } catch (Exception e) {
            R.error("获取文章信息失败");
        }
        return R.success().put("page", pageUtil);
    }

}
