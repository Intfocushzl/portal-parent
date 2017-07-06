package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;

import java.util.List;

/**
 * 专题信息表
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public interface BusinessmanSubjectInfoMapper extends BaseMapper<BusinessmanSubjectInfo> {

    List<BusinessmanSubjectInfo> acticleSubjectSelected();

    List<BusinessmanSubjectInfo> getActicleSubjectSelected();

}
