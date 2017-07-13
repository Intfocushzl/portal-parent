package com.yonghui.portal.service.channelTransparency;

import java.util.List;
import java.util.Map;

/**通道透明化所用的菜单服务部分方法 以后可以共用
 * Created by Shengwm on 2017/7/4.
 */
public interface MenuService {
    Map<String, Object> getShopByShopId(String shopId) throws Exception;

    List<Map<String, Object>> getAllBroveStoreList() throws Exception;
}
