package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.sys.SysLog;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/2.
 */
public interface UserAdminService {

    void savelog(SysLog sysLog);

    User queryObject(Integer id);

    User queryObjectAll(Long id);

    List<User> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(User user);

    void update(User user);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    int updateStatus(User user);

    List<User> queryChangeGrantList(Map<String, Object> map);

    int queryChangeGrantTotal(Map<String, Object> map);

    int pass(User user);

    int refuse(String userId);

    List<User> queryNewUserList(Map<String, Object> map);

    int queryNewUserTotal(Map<String, Object> map);
}
