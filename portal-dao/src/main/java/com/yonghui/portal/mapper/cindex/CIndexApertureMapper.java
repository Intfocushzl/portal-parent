package com.yonghui.portal.mapper.cindex;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.cindex.CIndexAperture;

import java.util.List;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
public interface CIndexApertureMapper extends BaseMapper<CIndexAperture> {
    List<CIndexAperture> queryListOpt();
}
