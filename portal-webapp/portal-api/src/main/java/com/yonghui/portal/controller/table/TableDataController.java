package com.yonghui.portal.controller.table;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.table.TableTitle;
import com.yonghui.portal.service.table.TableDataService;
import com.yonghui.portal.util.ListToTreeUtils;
import com.yonghui.portal.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**前端获取报表控制类
 * Created by Shengwm on 2017/5/22.
 */
@RestController
@RequestMapping("table")
public class TableDataController {
    @Reference
    private TableDataService tableDataService;

    /**
     * 表头
     * @param request
     * @param reportcode
     * @return
     */
    @RequestMapping("title")
    @ResponseBody
    @IgnoreAuth
    public R getTableTileByReportCode(HttpServletRequest request,String reportcode){
       // List<Map<String,Object>> maps = tableDataService.getTableTileByReportCode(reportcode);
        List<TableTitle> titles = tableDataService.getTableTitle(reportcode);
        List<TableTitle> list = new ListToTreeUtils<>().listTreeTableTitle(titles);
        return  R.success(list);
    }

}
