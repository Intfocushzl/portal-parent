package com.yonghui.portal.mapper.app;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.app.AppUserRoles;

import java.util.List;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public interface AppUserRolesMapper extends BaseMapper<AppUserRoles> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Integer> queryRoleIdList(Integer userId);
}
