package com.yonghui.portal.service.report;

import com.yonghui.portal.model.report.ReportModulePage;

import java.util.List;
import java.util.Map;

/**
 * 报表专题信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-16 11:23:25
 */
public interface ReportModulePageService {

    ReportModulePage queryObject(Long id);

    List<ReportModulePage> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ReportModulePage reportModulePage);

    void update(ReportModulePage reportModulePage);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
