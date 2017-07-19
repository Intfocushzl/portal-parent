package com.yonghui.portal.service.common;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/07/18
 * Description :
 */
public interface CommonService {

    List<Map<String, Object>> getDataList(Map<String, Object> params) throws Exception;

}
