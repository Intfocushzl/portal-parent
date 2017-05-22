package com.yonghui.portal.service.impl.table;

import com.yonghui.portal.mapper.table.TableDataMapper;
import com.yonghui.portal.service.table.TableDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**前端获取报表service
 * Created by Shengwm on 2017/5/22.
 */
@Service("tableDataService")
public class TableDataServiceImpl implements TableDataService {
    @Resource
    private TableDataMapper tableDataMapper;
    @Override
    public List<Map<String, Object>> getTableTileByReportCode(String reportcode) {
        return tableDataMapper.getTableTileByReportCode(reportcode);
    }
}
