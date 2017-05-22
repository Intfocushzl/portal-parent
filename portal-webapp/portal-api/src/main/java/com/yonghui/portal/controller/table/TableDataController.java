package com.yonghui.portal.controller.table;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.service.table.TableDataService;
import com.yonghui.portal.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**前端获取报表控制类
 * Created by Shengwm on 2017/5/22.
 */
@RestController
@RequestMapping("table")
public class TableDataController {
    @Reference
    private TableDataService tableDataService;

    @RequestMapping("title")
    @ResponseBody
    public R getTableTileByReportCode(HttpServletRequest request,String reportcode){
        return  R.error();
    }
}
