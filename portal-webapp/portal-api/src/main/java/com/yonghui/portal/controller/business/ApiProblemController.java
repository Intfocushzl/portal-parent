package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.businessman.BusinessmanProblem;
import com.yonghui.portal.model.businessman.ImgsInfo;
import com.yonghui.portal.service.business.ApiProblemService;
import com.yonghui.portal.service.sys.ApiImgsInfoService;
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
 * 用户问题反馈
 * 张海 2017.06.07 modify by liuwei
 */
@RestController
@RequestMapping("/api/business")
public class ApiProblemController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Reference
    private ApiProblemService apiProblemService;
    @Reference
    public ApiImgsInfoService apiImgsInfoService;

    /**
     * 获取用户问题反馈
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "problem")
    public R problem(HttpServletRequest req, HttpServletResponse response) {
        return R.success();
    }


    /**
     * 获取用户返回问题信息
     *
     * @param req
     * @param response
     * @param params
     * @return
     */
    @OpenAuth
    @RequestMapping(value = "problemList", method = RequestMethod.GET)
    public R problemList(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        PageUtils pageUtil = null;
        try {
            ApiQuery query = new ApiQuery(params);
            List<Map<String, Object>> problemList = apiProblemService.problemList(query);
            int total = apiProblemService.queryTotal(query);
            pageUtil = new PageUtils(problemList, total, query.getLimit(), query.getPage());
        } catch (Exception e) {
            return R.error("获取用户返回问题信息失败");
        }
        return R.success().put("page", pageUtil);
    }

    @OpenAuth
    @RequestMapping(value = "problemDetail", method = RequestMethod.GET)
    public R problemDetail(HttpServletRequest req, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        List<Map<String, Object>> problemDetail = null;
        try {
            problemDetail = apiProblemService.problemDetail(params);
        } catch (Exception e) {
            return R.error("获取用户返回问题信息失败");
        }
        return R.success(problemDetail);
    }


    /**
     * 用户添加问题反馈
     */
    @OpenAuth
    @RequestMapping(value = "saveProblem", method = RequestMethod.POST)
    public R saveProblem(HttpServletRequest req, HttpServletResponse response, BusinessmanProblem businessmanProblem) {
        try {
            if (businessmanProblem != null && businessmanProblem.getImages() != null) {
                String[] str = businessmanProblem.getImages().substring(1, businessmanProblem.getImages().length() - 1).split(",");
                ImgsInfo imgsInfo = null;
                for (String item : str) {
                    imgsInfo = new ImgsInfo();
                    imgsInfo.setImgPath(item);
                    imgsInfo.setRemark("App");
                    apiImgsInfoService.save(imgsInfo);
                }
            }
            apiProblemService.save(businessmanProblem);
        } catch (Exception e) {
            return R.error("获取用户返回问题信息失败");
        }
        return R.success();
    }

}
