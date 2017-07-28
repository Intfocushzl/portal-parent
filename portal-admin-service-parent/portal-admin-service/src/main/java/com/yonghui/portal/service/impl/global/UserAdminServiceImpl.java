package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.UserMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.global.UserAdminService;
import com.yonghui.portal.util.Md5Util;
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
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void savelog(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }
    @Override
    public User queryObject(Integer id) {
        return userMapper.queryObject(id);
    }

    @Override
    public User queryObjectAll(Long id) {
        return userMapper.queryObjectAll(id+"");
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
    public int updateStatus(User user) {
//        user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
        return userMapper.update(user);
    }

    @Override
    public List<User> queryChangeGrantList(Map<String, Object> map) {
        return userMapper.queryChangeGrantList(map);
    }

    @Override
    public int queryChangeGrantTotal(Map<String, Object> map) {
        return userMapper.queryChangeGrantTotal(map);
    }

    @Override
    public int pass(User user) {
        if (user.getChangeRoleId() != null && user.getChangeRoleId() != "") {
            user.setRoleId(Integer.parseInt(user.getChangeRoleId()));
            user.setChangeRoleId(null);
        }
        if (user.getChangeLargeArea() != null && user.getChangeLargeArea() != "") {
            user.setLargeArea(user.getChangeLargeArea());
            user.setChangeLargeArea("");
        }
        if (user.getChangeAreaMans() != null && user.getChangeAreaMans() != "") {
            user.setAreaMans(user.getChangeAreaMans());
            user.setChangeAreaMans("");
        }
        if (user.getChangeStoreNumber() != null && user.getChangeStoreNumber() != "") {
            user.setStoreNumber(user.getChangeStoreNumber());
            user.setChangeStoreNumber("");
        }
        if (user.getChangeFirm() != null && user.getChangeFirm() != "") {
            user.setFirm(user.getChangeFirm());
            user.setChangeFirm("");
        }
        user.setChangeStatus(0);
        return userMapper.update(user);
    }

    @Override
    public int refuse(String userId) {
        return userMapper.refuse(userId);
    }

    @Override
    public List<User> queryNewUserList(Map<String, Object> map) {
        return userMapper.queryNewUserList(map);
    }

    @Override
    public int queryNewUserTotal(Map<String, Object> map) {
        return userMapper.queryNewUserTotal(map);
    }
}
