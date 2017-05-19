package com.yonghui.portal.mapper.global;

import com.yonghui.portal.model.global.Notice;

import java.util.List;
import java.util.Map;


public interface NoticeMapper {

	Long add(Notice notice);

	List<Notice> list(Map<String, Object> sqlparm);

	Long total(Map<String, Object> sqlparm);

	void delete(int id);

	void delByIdHide(int id);

	int addByIdHide(int id);

	List<Notice> getNewList();
	
	Notice showDetail(int id);

	void update(Map<String, Object> sqlPram);
}