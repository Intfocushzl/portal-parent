package com.yonghui.portal.mapper.report;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.report.PortalRouteReport;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:43:38
 */
public interface PortalRouteReportMapper extends BaseMapper<PortalRouteReport> {

    /**
     * 根据编码查询
     * @param code
     * @return
     */
    PortalRouteReport queryObjectByCode(@Param("code") String code);

    /**
     * 产生新的编码
     *
     * @return
     */
    String getNewMaxCode();
}
