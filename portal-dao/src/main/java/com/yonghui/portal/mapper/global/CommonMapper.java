package com.yonghui.portal.mapper.global;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/07/18
 * Description :
 */
public interface CommonMapper {

    List<Map<String,Object>> getDataList(Map<String,Object> params) throws Exception ;
}
