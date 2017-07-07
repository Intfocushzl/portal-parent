package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;
import com.yonghui.portal.service.business.ApiSubjectInfoService;
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

/**
 * 专题信息表
 *
 * @author 刘伟
 * @date 2017-07-07
 */

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business", description = "专题信息查询")
public class ApiSubjectInfoController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private ApiSubjectInfoService apiSubectInfoService;


    /**
     * 专题信息
     */
    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    @ApiOperation(value = "专题信息查询", produces = MediaType.APPLICATION_JSON_VALUE, notes = "查询专题信息", response = R.class)
    public R list(HttpServletRequest request, HttpServletResponse response) {
        List<BusinessmanSubjectInfo> list = null;
        try {
            list = apiSubectInfoService.getActicleSubjectSelected();
        } catch (Exception e) {
            return R.error("专题信息查询失败!!!" + e.getMessage());
        }
        return R.success(list);
    }
}
