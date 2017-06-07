package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户收藏
 * 张海 2017.06.07
 */
@RestController
@RequestMapping("/api/business")
public class ApiFavoritesController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;

    /**
     * 获取用户收藏
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "favorites")
    public R favorites(HttpServletRequest req, HttpServletResponse response) {
        return R.success();
    }

}
