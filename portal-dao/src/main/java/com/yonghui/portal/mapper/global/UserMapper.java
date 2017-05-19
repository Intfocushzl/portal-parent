package com.yonghui.portal.mapper.global;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.global.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper  extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(User record) throws Exception;

    int insertSelective(User record) throws Exception;

    User selectByPrimaryKey(Integer id) throws Exception;

    User getUserByJobNumber(@Param(value = "jobNumber") String jobNumber) throws Exception;

    User getUserById(@Param(value = "id") int id) throws Exception;

    int updateByPrimaryKeySelective(User record) throws Exception;

    int updateByPrimaryKey(User record) throws Exception;

    User Login(@Param(value = "jobNumber") String jobNumber, @Param(value = "pass") String pass) throws Exception;

    List<User> userManageList(Map<String, Object> map) throws Exception;

    int userManageListSize(Map<String, Object> map) throws Exception;

    List<User> changeGranUserManageList(Map<String, Object> map) throws Exception;

    int changeGranUserManageListSize(Map<String, Object> map) throws Exception;

    Map<String, Object> getPersonnelMattersStatus(@Param(value = "jobNumber") String jobNumber) throws Exception;

    Map<String, Object> getNewBusiness(@Param(value = "jobNumber") String jobNumber) throws Exception;

    List<Map<String, Object>> getBrowesReportByList(@Param(value = "userId") int userId) throws Exception;


}