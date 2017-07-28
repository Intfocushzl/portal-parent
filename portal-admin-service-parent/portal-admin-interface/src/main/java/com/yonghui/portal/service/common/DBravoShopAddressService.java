package com.yonghui.portal.service.common;


import com.yonghui.portal.model.common.DBravoShopAddress;
import com.yonghui.portal.model.sys.SysLog;

import java.util.List;
import java.util.Map;

/**
 * 门店地址
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-07-17 19:56:17
 */
public interface DBravoShopAddressService {

    void savelog(SysLog sysLog);

    DBravoShopAddress queryObject(Long id);

    List<DBravoShopAddress> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(DBravoShopAddress dBravoShopAddress);

    void update(DBravoShopAddress dBravoShopAddress);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
