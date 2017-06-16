package com.yonghui.portal.mapper.global;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.global.Notice;

import java.util.List;
import java.util.Map;


public interface NoticeMapper extends BaseMapper<Notice> {

	List<Notice> getNewList();
	
	Notice showDetail(int id);
}