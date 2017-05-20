package com.yonghui.portal.service.horse;

import java.util.List;
import java.util.Map;


public interface DataMapIndexApertureService {

    List<String> getindexlist(String theme);

    List<String> getthemelist();

    List<Map<String, Object>> getDataMapIndexAperture(String theme, String index);

}
