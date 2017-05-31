package com.yonghui.portal.service.app;

import java.util.List;

/**
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public interface AppUserRolesService {

    void saveOrUpdate(Long userId, List<Integer> roleIdList);

    /**
     * 根据用户ID,获取角色ID列表
     */
    List<Integer> queryRoleIdList(Integer userId);

    /*
    * 删除某个用户的角色
    * */
    void delete(Integer userId);

}
