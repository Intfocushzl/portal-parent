package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.UserMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User record) throws Exception {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) throws Exception {
        return userMapper.insertSelective(record);
    }

    @Override
    public User getUserByJobNumber(String jobNumber) throws Exception {
        return userMapper.getUserByJobNumber(jobNumber);
    }

    @Override
    public User Login(String jobNumber, String pass) throws Exception {
        return userMapper.Login(jobNumber, pass);
    }

    @Override
    public Map<String, Object> getPersonnelMattersStatus(String jobNumber) throws Exception {
        return userMapper.getPersonnelMattersStatus(jobNumber);
    }

    @Override
    public int updatePasswordByJobNumber(String jobNumber, String password) {
        Map<String, Object> map = new HashedMap();
        map.put("jobNumber", jobNumber);
        map.put("pass", password);
        return userMapper.updatePasswordByJobNumber(map);
    }

    @Override
    public int update(User user) throws Exception {
        return userMapper.update(user);
    }

}
