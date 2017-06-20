package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.UserMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserAdminService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/2.
 */
@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryObject(Integer id) {
        return userMapper.queryObject(id);
    }

    @Override
    public List<User> queryList(Map<String, Object> map) {
        return userMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userMapper.queryTotal(map);
    }

    @Override
    public void save(User user) {
        String account = user.getJobNumber().trim();
        user.setAccount(account);

        if (user.getLargeArea() != null && user.getLargeArea().equals("全部")) {
            user.setLargeArea("ALL");
        }
        if (user.getProvince() != null && user.getProvince().equals("全部")) {
            user.setProvince("ALL");
        }
        if (user.getCity() != null && user.getCity().equals("全部")) {
            user.setCity("ALL");
        }
        user.setStatus(1);
        if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
            user.setStoreNumber("ALL");
        }
        user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
        userMapper.save(user);
    }

    @Override
    public void update(User user) {
        String account = user.getJobNumber().trim();
        user.setAccount(account);
        user.setAccount(user.getJobNumber().trim());

        if (user.getLargeArea() != null && user.getLargeArea().equals("全部")) {
            user.setLargeArea("ALL");
        }
        if (user.getProvince() != null && user.getProvince().equals("全部")) {
            user.setProvince("ALL");
        }
        if (user.getCity() != null && user.getCity().equals("全部")) {
            user.setCity("ALL");
        }
        user.setStatus(user.getStatus());
        if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
            user.setStoreNumber("ALL");
        }
//        user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        userMapper.deleteBatch(ids);
    }

    @Override
    public void updateStatus(User user) {
//        user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
        int res = userMapper.update(user);
        if (res == 1) {
            // TODO: 2017/6/9 0009   调用APP注册接口
        } else {

        }
    }

    @Override
    public List<User> queryChangeGrantList(Map<String, Object> map) {
        return userMapper.queryChangeGrantList(map);
    }

    @Override
    public int queryChangeGrantTotal(Map<String, Object> map) {
        return userMapper.queryChangeGrantTotal(map);
    }
}
