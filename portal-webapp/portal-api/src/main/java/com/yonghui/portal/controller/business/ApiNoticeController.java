package com.yonghui.portal.controller.business;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.service.business.ApiNoticeService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.ApiQuery;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统公告
 * 张海 2017.06.07
 */
@RestController
@RequestMapping("/api/business")
public class ApiNoticeController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Reference
    private ApiNoticeService apiNoticeService;

    /**
     * 获取系统公告
     */
    @RequestMapping(value = "notice")
    @OpenAuth
    public R notice(@RequestParam Map<String,Object> params) {
        BusinessmanNotice bsn = apiNoticeService.notice(params);
        if(bsn != null){
            return R.success().setData(bsn);
        }else{
            return R.error("没找到对应详情");
        }
    }

    //
    @RequestMapping(value="noticeList")
    @OpenAuth
    public R noticeList(@RequestParam Map<String, Object> params){
        String noticeType =(String)params.get("noticeType");
        String[] array = noticeType.split(",");
        params.put("noticeType",array);
        //查询列表数据
        ApiQuery query = new ApiQuery(params);
        List<Map<String,Object>> list = apiNoticeService.noticeList(query);
        int total = apiNoticeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.success().put("page", pageUtil);

    }

}
