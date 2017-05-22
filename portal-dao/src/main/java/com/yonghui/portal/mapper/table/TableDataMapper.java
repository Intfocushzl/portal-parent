package com.yonghui.portal.mapper.table;

import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/5/22.
 */
public interface TableDataMapper {
    //根据报表编码取出标题信息
    List<Map<String,Object>> getTableTileByReportCode(String reportcode);
}
