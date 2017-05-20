package com.yonghui.portal.mapper.horse;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DataMapIndexApertureMapper {

    List<String> getindexlist(@Param("theme") String theme);

    List<String> getthemelist();

    List<Map<String, Object>> getDataMapIndexAperture(@Param("theme") String theme, @Param("index") String index);
}
