package com.yonghui.portal.service.impl.common;

import com.yonghui.portal.mapper.common.DBravoShopAddressMapper;
import com.yonghui.portal.model.common.DBravoShopAddress;
import com.yonghui.portal.service.common.DBravoShopAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("dBravoShopAddressService")
public class DBravoShopAddressServiceImpl implements DBravoShopAddressService {
    @Autowired
    private DBravoShopAddressMapper dBravoShopAddressMapper;

    @Override
    public DBravoShopAddress queryObject(Long id) {
        return dBravoShopAddressMapper.queryObject(id);
    }

    @Override
    public List<DBravoShopAddress> queryList(Map<String, Object> map) {
        return dBravoShopAddressMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return dBravoShopAddressMapper.queryTotal(map);
    }

    @Override
    public void save(DBravoShopAddress dBravoShopAddress) {
        dBravoShopAddressMapper.save(dBravoShopAddress);
    }

    @Override
    public void update(DBravoShopAddress dBravoShopAddress) {
        dBravoShopAddressMapper.update(dBravoShopAddress);
    }

    @Override
    public void delete(Long id) {
        dBravoShopAddressMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        dBravoShopAddressMapper.deleteBatch(ids);
    }

}
