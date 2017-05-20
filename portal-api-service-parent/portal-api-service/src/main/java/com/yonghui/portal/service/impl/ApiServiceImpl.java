package com.yonghui.portal.service.impl;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.report.PortalExecuteSql;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.model.report.PortalReport;
import com.yonghui.portal.service.ApiDataBaseSqlService;
import com.yonghui.portal.service.ApiService;
import com.yonghui.portal.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.yonghui.portal.util.StringUtils.getSqlByParameter;

/**
 * 实现ApiService接口
 * Created by 张海 on 2017/05/11
 */
public class ApiServiceImpl implements ApiService {

    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private ApiDataBaseSqlService apiDataBaseSqlService;

    /**
     * 调用存储过程获取结果集
     *
     * @param parameter 请求参数 如：aa=AAA@@bb=CC@@dd=DD
     * @return
     */
    @Override
    public List<Map<String, Object>> getListResultListMapByPro(PortalReport report, PortalProcedure portalPro, PortalDataSource portalDataSource, String parameter) {
        String proParameter = StringUtils.getParameter(parameter, portalPro.getParameter());
        StringBuffer sb = new StringBuffer();
        sb.append("{call ");
        if (!StringUtils.isEmpty(portalPro.getProdb())) {
            sb.append(portalPro.getProdb() + ".");
        }
        sb.append(portalPro.getProname() + " (" + proParameter + ")}");
        log.info("请求的参数：parameter" + parameter);
        log.info("执行的存储过程:" + sb.toString());
        return apiDataBaseSqlService.queryCallPro(sb.toString(), portalDataSource);
    }

    /**
     * 执行sql获取结果集
     *
     * @param parameter 请求参数 如：aa=AAA@@bb=CC@@dd=DD
     * @return
     */
    @Override
    public List<Map<String, Object>> getListResultListMapBySql(PortalReport report, PortalExecuteSql portalExecuteSql, PortalDataSource portalDataSource, String parameter) {
        // 封装执行的sql语句
        String sql = getSqlByParameter(portalExecuteSql.getExecuteSql(), parameter, portalExecuteSql.getParameter());
        log.info("请求的参数：parameter" + parameter);
        log.info("执行的sql:" + sql);
        return apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
    }

}
