package com.yonghui.portal.service.table;

import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/5/22.
 */
public interface TableDataService {
    List<Map<String,Object>> getTableTileByReportCode(String reportcode);
}
