package com.yonghui.portal.service.impl.global;


import com.yonghui.portal.mapper.global.UserMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) throws Exception {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) throws Exception {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByJobNumber(String jobNumber) throws Exception {
        return userMapper.getUserByJobNumber(jobNumber);
    }

    @Override
    public User getUserById(int id) throws Exception {
        return userMapper.getUserById(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) throws Exception {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) throws Exception {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User Login(String jobNumber, String pass) throws Exception {
        return userMapper.Login(jobNumber, pass);
    }

    @Override
    public List<User> userManageList(Map<String, Object> map) throws Exception {
        return userMapper.userManageList(map);
    }

    @Override
    public int userManageListSize(Map<String, Object> map) throws Exception {
        return userMapper.userManageListSize(map);
    }

    @Override
    public List<User> changeGranUserManageList(Map<String, Object> map) throws Exception {
        return userMapper.changeGranUserManageList(map);
    }

    @Override
    public int changeGranUserManageListSize(Map<String, Object> map) throws Exception {
        return userMapper.changeGranUserManageListSize(map);
    }

    @Override
    public Map<String, Object> getPersonnelMattersStatus(String jobNumber) throws Exception {
        return userMapper.getPersonnelMattersStatus(jobNumber);
    }


    @Override
    public List<Map<String, Object>> getBrowesReportByList(int userId) throws Exception {
        return userMapper.getBrowesReportByList(userId);
    }
}
