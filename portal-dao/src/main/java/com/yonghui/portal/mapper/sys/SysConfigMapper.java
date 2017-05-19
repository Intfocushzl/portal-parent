package com.yonghui.portal.mapper.sys;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.sys.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 * Created by 张海 on 2017/04/29.
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {
	
	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);
	
	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
