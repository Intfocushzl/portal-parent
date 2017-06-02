package com.yonghui.portal.service.cindex;

import com.yonghui.portal.model.cindex.ReportDimIndex;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
public interface ReportDimIndexService {

    ReportDimIndex queryObject(Integer id);

    List<ReportDimIndex> queryList(Map<String, Object> map);

    List<ReportDimIndex> queryListOpt();

    int queryTotal(Map<String, Object> map);

    void save(ReportDimIndex reportDimIndex);

    void update(ReportDimIndex reportDimIndex);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
