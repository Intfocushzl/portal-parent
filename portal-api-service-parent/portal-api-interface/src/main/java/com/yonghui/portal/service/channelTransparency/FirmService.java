package com.yonghui.portal.service.channelTransparency;

import java.util.Map;

/**通道透明化所用的商行服务部分方法 以后可以共用
 * Created by Shengwm on 2017/7/4.
 */
public interface FirmService {
  Map<String ,Object> getGroupByGroupId(String groupId);
}
