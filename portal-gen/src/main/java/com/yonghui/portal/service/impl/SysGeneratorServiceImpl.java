package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.SysGeneratorMapper;
import com.yonghui.portal.model.SysGenerator;
import com.yonghui.portal.service.SysGeneratorService;
import com.yonghui.portal.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {
	@Autowired
	private SysGeneratorMapper sysGeneratorMapper;

	@Override
	public SysGenerator queryObject(Long id) {
		return sysGeneratorMapper.queryObject(id);
	}

	@Override
	public void save(SysGenerator sysGenerator) {
		sysGeneratorMapper.save(sysGenerator);
	}

	@Override
	public void update(SysGenerator sysGenerator) {
		sysGeneratorMapper.update(sysGenerator);
	}

	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sysGeneratorMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorMapper.queryTotal(map);
	}

	@Override
	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorMapper.queryTable(tableName);
	}

	@Override
	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorMapper.queryColumns(tableName);
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		//包名称，作者信息以数据库配置为准
		SysGenerator sysGenerator = sysGeneratorMapper.queryObject(1L);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip, sysGenerator);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
