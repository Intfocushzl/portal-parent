package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.api.PortalDataSourceMapper;
import com.yonghui.portal.mapper.api.PortalExecuteSqlMapper;
import com.yonghui.portal.mapper.api.PortalProcedureMapper;
import com.yonghui.portal.mapper.api.PortalReportMapper;
import com.yonghui.portal.model.api.PortalDataSource;
import com.yonghui.portal.model.api.PortalExecuteSql;
import com.yonghui.portal.model.api.PortalProcedure;
import com.yonghui.portal.model.api.PortalReport;
import com.yonghui.portal.service.ApiDataBaseSqlService;
import com.yonghui.portal.service.ApiService;
import com.yonghui.portal.util.RRException;
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
    private PortalProcedureMapper portalProcedureMapper;
    @Autowired
    private PortalReportMapper portalReportMapper;
    @Autowired
    private PortalDataSourceMapper portalDataSourceMapper;
    @Autowired
    private ApiDataBaseSqlService apiDataBaseSqlService;
    @Autowired
    private PortalExecuteSqlMapper portalExecuteSqlMapper;

    /**
     * JDBC调用存储过程获取结果集
     *
     * @param code      报表(业务数据结果集）编码
     * @param parameter 请求参数 如：aa=AAA@@bb=CC@@dd=DD
     * @return
     */
    @Override
    public List<Map<String, Object>> jdbcProListResultListMapByParam(String code, String parameter) {
        PortalReport report = portalReportMapper.queryObjectByCode(code);
        if (report == null || StringUtils.isEmpty(report.getExecuteCode())) {
            throw new RRException("请求编码:" + code + "无效，或没有指定存储过程");
        } else if (report.getExecuteType() == 1) {
            return executePro(report.getExecuteCode(), parameter);
        } else if (report.getExecuteType() == 2) {
            return executeSql(report.getExecuteCode(), parameter);
        }
        return null;
    }

    /**
     * 执行存储过程
     *
     * @param excuteCode 执行编码
     * @param parameter  执行参数
     * @return
     */
    private List<Map<String, Object>> executePro(String excuteCode, String parameter) {
        PortalProcedure portalPro = portalProcedureMapper.queryObjectByProcode(excuteCode);
        if (portalPro == null || StringUtils.isEmpty(portalPro.getProname())) {
            throw new RRException("请求编码:" + excuteCode + "无效，没有找到存储过程");
        }
        String proParameter = null;
        try {
            proParameter = StringUtils.getParameter(parameter, portalPro.getParameter());
        } catch (Exception e) {
            log.error("存储过程参数封装异常parameter：" + parameter + " portalParameter:" + portalPro.getParameter());
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{call ");
        if (!StringUtils.isEmpty(portalPro.getProdb())) {
            sb.append(portalPro.getProdb() + ".");
        }
        sb.append(portalPro.getProname() + " (" + proParameter + ")}");
        log.info("请求参数：parameter" + parameter);
        log.info("执行sql:" + sb.toString());
        PortalDataSource portalDataSource = portalDataSourceMapper.queryObjectByCode(portalPro.getDataSourceCode());
        return apiDataBaseSqlService.queryCallPro(sb.toString(), portalDataSource);
    }

    /**
     * 执行sql语句
     * 防sql注入，查询页数，导出是不限制页数
     * 复杂sql查询参数解析
     *
     * @param excuteCode 执行编码
     * @param parameter  执行参数
     * @return
     */
    private List<Map<String, Object>> executeSql(String excuteCode, String parameter) {
        PortalExecuteSql portalExecuteSql = portalExecuteSqlMapper.queryObjectBySqlcode(excuteCode);
        if (portalExecuteSql == null || StringUtils.isEmpty(portalExecuteSql.getExecuteSql())) {
            throw new RRException("请求编码:" + excuteCode + "无效，没有找到执行语句");
        }
        // 封装执行的sql语句
        String sql = getSqlByParameter(portalExecuteSql.getExecuteSql(), parameter, portalExecuteSql.getParameter());
        log.info("请求参数：parameter" + parameter);
        log.info("执行sql:" + sql);
        PortalDataSource portalDataSource = portalDataSourceMapper.queryObjectByCode(portalExecuteSql.getDataSourceCode());
        return apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
    }

}
