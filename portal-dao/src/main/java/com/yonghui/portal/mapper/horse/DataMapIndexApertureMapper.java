package com.yonghui.portal.mapper.horse;

import com.yonghui.portal.model.horse.DataMapIndexAperture;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DataMapIndexApertureMapper {

    List<String> getindexlist(@Param("theme") String theme);

    List<String> getthemelist();

    List<DataMapIndexAperture> getDataMapIndexAperture(@Param("theme") String theme, @Param("index") String index);
}
