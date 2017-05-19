package com.yonghui.portal.service.horse;

import com.yonghui.portal.model.horse.DataMapIndexAperture;

import java.util.List;


public interface DataMapIndexApertureService {

    List<String> getindexlist(String theme);

    List<String> getthemelist();

    List<DataMapIndexAperture> getDataMapIndexAperture(String theme, String index);

}
