package com.yonghui.portal.service.cindex;

import com.yonghui.portal.model.cindex.CIndexRefer;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
public interface CIndexReferService {

    CIndexRefer queryObject(Integer id);

    List<CIndexRefer> queryList(Map<String, Object> map);

    List<CIndexRefer> queryListOpt();

    int queryTotal(Map<String, Object> map);

    void save(CIndexRefer cIndexRefer);

    void update(CIndexRefer cIndexRefer);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
