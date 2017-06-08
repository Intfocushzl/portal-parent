package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.businessman.BusinessmanComment;
import com.yonghui.portal.service.business.ApiCommentService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
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
 * 用户评论信息
 * 张海 2017.06.07 modify by liuwei
 */
@RestController
@RequestMapping("/api/business")
public class ApiCommentController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Reference
    private ApiCommentService apiCommentService;

    /**
     * 获取用户评论信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "commentList" , method = RequestMethod.GET)
    public R commentList(HttpServletRequest req, HttpServletResponse response , @RequestParam Map<String, Object> params) {
        PageUtils pageUtil = null ;
        try {
            Query query = new Query(params);
            List<BusinessmanComment> portalDataSourceList = apiCommentService.queryList(query);
            int total = apiCommentService.queryTotal(query);
             pageUtil = new PageUtils(portalDataSourceList, total, query.getLimit(), query.getPage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success().put("page", pageUtil);
    }

    /**
     *保存评论
     */
    @RequestMapping(value = "saveComment" , method = RequestMethod.GET)
    public R saveComment(HttpServletRequest req, HttpServletResponse response , @RequestParam Map<String, Object> params) {
        try {
         //   apiCommentService.save(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success();
    }
}
