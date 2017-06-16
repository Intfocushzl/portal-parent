package com.yonghui.portal.service.cindex;


import com.yonghui.portal.model.cindex.CIndexAperture;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
public interface CIndexApertureService {

    CIndexAperture queryObject(Integer id);

    List<CIndexAperture> queryList(Map<String, Object> map);

    List<CIndexAperture> queryListOpt();

    int queryTotal(Map<String, Object> map);

    void save(CIndexAperture cIndexAperture);

    void update(CIndexAperture cIndexAperture);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
