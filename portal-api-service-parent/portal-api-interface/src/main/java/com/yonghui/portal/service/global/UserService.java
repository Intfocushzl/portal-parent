package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.User;

import java.util.List;
import java.util.Map;


public interface UserService {


    int insert(User record) throws Exception;

    int insertSelective(User record) throws Exception;

    User getUserByJobNumber(String jobNumber) throws Exception;

    User Login(String jobNumber, String pass) throws Exception;

    Map<String, Object> getPersonnelMattersStatus(String jobNumber) throws Exception;

    int updatePasswordByJobNumber(String jobNumber, String password);

    int update(User user)throws Exception;
}
