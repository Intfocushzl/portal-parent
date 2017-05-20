package com.yonghui.portal.mapper.api;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.report.PortalProcedure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用调度存储过程
 * Created by 张海 on 2017/05/11
 */
public interface PortalProcedureMapper extends BaseMapper<PortalProcedure> {

    /**
     * 批量删除对象
     *
     * @param procodes
     * @return
     */
    int deleteBatchByProcodes(String[] procodes);

    /**
     * 根据编码查询
     * @param procode
     * @return
     */
    PortalProcedure queryObjectByProcode(@Param("procode") String procode);

    /**
     * 调用存储过程
     * parameter 如：aa,bb,cc
     *
     * @return
     */
    Map<String, Object> callProResultMapByParam(@Param("proname") String proname, @Param("parameter") String parameter);



    /**
     * 调用存储过程
     * parameter 如：aa,bb,cc
     *
     * @return
     */
    List<Map> callProListResultListMapByParam(@Param("proname") String proname, @Param("parameter") String parameter);

}
