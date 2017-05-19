package com.yonghui.portal.service.impl.global;


import com.yonghui.portal.mapper.global.UserMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryObject(Integer id){
        return userMapper.queryObject(id);
    }

    @Override
    public List<User> queryList(Map<String, Object> map){
        return userMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return userMapper.queryTotal(map);
    }

    @Override
    public void save(User user){
        String account=user.getJobNumber().trim();
        user.setAccount(account);

        if (user.getLargeArea()!=null&&user.getLargeArea().equals("全部")) {
            user.setLargeArea("ALL");
        }
        if (user.getProvince()!=null&&user.getProvince().equals("全部")) {
            user.setProvince("ALL");
        }
        if (user.getCity()!=null&&user.getCity().equals("全部")) {
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
    public void update(User user){
        String account=user.getJobNumber().trim();
        user.setAccount(account);
        user.setStatus(1);
        user.setAccount(user.getJobNumber().trim());

        if (user.getLargeArea()!=null&&user.getLargeArea().equals("全部")) {
            user.setLargeArea("ALL");
        }
        if (user.getProvince()!=null&&user.getProvince().equals("全部")) {
            user.setProvince("ALL");
        }
        if (user.getCity()!=null&&user.getCity().equals("全部")) {
            user.setCity("ALL");
        }
        user.setStatus(1);
        if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
            user.setStoreNumber("ALL");
        }
//        user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id){
        userMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids){
        userMapper.deleteBatch(ids);
    }




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
