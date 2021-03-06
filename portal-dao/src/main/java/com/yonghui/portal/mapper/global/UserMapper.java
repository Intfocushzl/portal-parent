package com.yonghui.portal.mapper.global;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.global.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper  extends BaseMapper<User> {

    int insert(User record) throws Exception;

    int insertSelective(User record) throws Exception;

    User getUserByJobNumber(@Param(value = "jobNumber") String jobNumber) throws Exception;

    User Login(@Param(value = "jobNumber") String jobNumber, @Param(value = "pass") String pass) throws Exception;

    Map<String, Object> getPersonnelMattersStatus(@Param(value = "jobNumber") String jobNumber) throws Exception;

    int updatePasswordByJobNumber(Map<String, Object> map);

    int updateInfoByJobNumber(Map<String, Object> map);

    int changeGrant(User record) throws Exception;
}