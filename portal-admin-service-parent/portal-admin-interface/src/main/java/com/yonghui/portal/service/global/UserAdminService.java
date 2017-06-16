package com.yonghui.portal.service.global;

import com.yonghui.portal.model.global.User;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/2.
 */
public interface UserAdminService {

    User queryObject(Integer id);

    List<User> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(User user);

    void update(User user);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    void updateStatus(User user);
}
