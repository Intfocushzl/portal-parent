package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.User;

import java.util.List;
import java.util.Map;


public interface UserService {

    User queryObject(Integer id);

    List<User> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(User user);

    void update(User user);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);



    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(User record) throws Exception;

    int insertSelective(User record) throws Exception;

    User selectByPrimaryKey(Integer id) throws Exception;

    User getUserByJobNumber(String jobNumber) throws Exception;

    User getUserById(int id) throws Exception;

    int updateByPrimaryKeySelective(User record) throws Exception;

    int updateByPrimaryKey(User record) throws Exception;

    User Login(String jobNumber, String pass) throws Exception;

    List<User> userManageList(Map<String, Object> map) throws Exception;

    int userManageListSize(Map<String, Object> map) throws Exception;

    List<User> changeGranUserManageList(Map<String, Object> map) throws Exception;

    int changeGranUserManageListSize(Map<String, Object> map) throws Exception;

    Map<String, Object> getPersonnelMattersStatus(String jobNumber) throws Exception;

    List<Map<String, Object>> getBrowesReportByList(int userId) throws Exception;
}
