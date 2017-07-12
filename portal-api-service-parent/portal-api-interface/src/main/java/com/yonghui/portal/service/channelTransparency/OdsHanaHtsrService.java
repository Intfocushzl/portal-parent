package com.yonghui.portal.service.channelTransparency;

import com.yonghui.portal.model.channelTransparency.OdsHanaHtsr;

import java.util.List;
import java.util.Map;

/**
 * Created by Shengwm on 2017/7/11.
 */
public interface OdsHanaHtsrService {

    List<Map<String,Object>> tlxList();

    List<Map<String,Object>> purchOrgList();

    List<Map<String,Object>> purGourpList();

    List<OdsHanaHtsr> getAllGalleryCost(Map<String, Object> map);

    List<OdsHanaHtsr> getAllZopc(String zopc);
}
