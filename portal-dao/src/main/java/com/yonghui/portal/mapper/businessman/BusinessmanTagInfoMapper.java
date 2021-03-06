package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanTagInfo;

import java.util.List;

/**
 * 标签信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanTagInfoMapper extends BaseMapper<BusinessmanTagInfo> {

    List<BusinessmanTagInfo> queryByTagType(Integer type);
}
