package com.yonghui.portal.controller.global;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.global.UserService;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.PageUtils;
import com.yonghui.portal.util.Query;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserForFrontController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private UserService userService;

    /**
     *
     * */
    @RequestMapping("getUserName")
    @ResponseBody
    @IgnoreAuth
    public R getUserName(HttpServletRequest request, HttpServletResponse response, String userNum) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, Object> map = null;
        try {
            log.info("getUserName" + userNum);
            map = userService.getPersonnelMattersStatus(userNum);
        } catch (Exception e) {
            return R.error("查找用户名异常！");
        }
        return R.success(map);
    }


/*

    @RequestMapping("manageAddUser")
    public String manageAddUser(HttpSession session, HttpServletRequest request, User user, int district) {
        try {

            user.setStatus(1);
            user.setAccount(user.getJobNumber().trim());

            if (user.getLargeArea().equals("全部")) {
                user.setLargeArea("ALL");
            }
            if (user.getProvince().equals("全部")) {
                user.setProvince("ALL");
            }
            if (user.getCity().equals("全部")) {
                user.setCity("ALL");
            }
            user.setStatus(1);
            user.setAccount(user.getJobNumber().trim());
            if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
                user.setStoreNumber("ALL");
            }
            user.setType(district);
            user.setPass(Md5Util.getMd5("MD5", 0, null, user.getPass()));
            userMapper.insertSelective(user);

            request.setAttribute("message", "注册成功");
            return "/login";

        } catch (Exception e) {
            request.setAttribute("message", "注册异常");
            log.error("注册异常+manageAddUser", e);
            return "/newReg1";
        }
    }

    @RequestMapping("manageUpdateUser")
    public String manageUpdateUser(HttpSession session, HttpServletRequest request, User user) {
        try {

            user.setStatus(1);
            user.setAccount(user.getJobNumber().trim());

            if (user.getLargeArea().equals("") || user.getLargeArea().equals("全部")) {
                user.setLargeArea("ALL");
            }
            if (user.getProvince().equals("") || user.getProvince().equals("全部")) {
                user.setProvince("ALL");
            }
            if (user.getCity().equals("") || user.getCity().equals("全部")) {
                user.setCity("ALL");
            }
            user.setStatus(1);
            user.setAccount(user.getJobNumber().trim());
            if (user.getStoreNumber() == null || user.getStoreNumber().equals("")) {
                user.setStoreNumber("ALL");
            }
            userMapper.updateByPrimaryKeySelective(user);

            request.setAttribute("message", "修改成功");
            return "/login";

        } catch (Exception e) {
            request.setAttribute("message", "修改异常");
            log.error("注册异常", e);
            return "/newReg1";
        }
    }

    @RequestMapping("changeGrant")
    @ResponseBody
    public int changeGrant(HttpSession session, HttpServletRequest request, User user) {
        int ret = 0;
        try {
            User retUser = (User) session.getAttribute("user");
            if (user.getChangeLargeArea().equals("全部")) {
                user.setChangeLargeArea("ALL");
            }

            if (user.getChangeProvince().equals("全部")) {
                user.setChangeProvince("ALL");
            }
            if (user.getChangeCity().equals("全部")) {
                user.setChangeCity("ALL");
            }
            user.setId(retUser.getId());
            user.setChangeStatus(1);
            ret = userMapper.updateByPrimaryKeySelective(user);

            return ret;

        } catch (Exception e) {
            log.error("申请权限变更异常", e);
            return 0;
        }
    }

    @RequestMapping("userManageList")
    @ResponseBody

    public String userManageList(HttpSession session, HttpServletRequest request, String keyWork, String keyWorkName,
                                 Integer status, Integer page, Integer rows) {
        JSONObject data = new JSONObject();
        Integer ownerType = null;
        try {
            if (page == null && rows == null) {
                page = 1;
                rows = 10;
            }
            //
            User user = null;
            user = (User) session.getAttribute("user");
            if (null == user) {

                session.setAttribute("msg", "链接超时请重新登录");

                return null;
            }

            Integer userType = null;
            //
            if (!"admin".equals(user.getAccount()) && user.getRoleId() != 10) {
                userType = user.getType();

                ownerType = roleMapper.selectByPrimaryKey(user.getRoleId()).getOwnerType();
            }
            data = userService.userManageList(keyWork, keyWorkName, status, page, rows,
                    null != user && null != user.getRoleId() ? (1 == user.getRoleId() ? null : user.getRoleId()) : null,
                    ownerType, userType);
            return JSONObject.toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            log.error("用户数据", e);
            return "";
        }
    }

    @RequestMapping("changeGranUserManageList")
    @ResponseBody
    public String changeGranUserManageList(HttpSession session, HttpServletRequest request, Integer page,
                                           Integer rows) {
        JSONObject data = new JSONObject();
        try {
            if (page == null && rows == null) {
                page = 1;
                rows = 10;
            }
            data = userService.changeGranUserManageList(page, rows);
            return JSONObject.toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            log.error("用户数据", e);
            return "";
        }
    }

    @RequestMapping("delUser")
    @ResponseBody
    public int delUser(HttpSession session, HttpServletRequest request, int userId, int status) {
        try {
            User user = new User();
            user.setId(userId);
            user.setStatus(status);
            return userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("冻结用户", e);
            return 0;
        }
    }

    @RequestMapping("updUser")
    @ResponseBody
    public int updUser(HttpSession session, HttpServletRequest request, int userId, int status) {
        try {
            User loginedUser = (User) session.getAttribute("user");
            User target = userMapper.getUserById(userId);
            if (null != target && null != loginedUser) {
                if (target.getRoleId() == loginedUser.getRoleId()) {
                    return -1;
                }
            } else {

                return -1;
            }

            User user = new User();
            user.setId(userId);
            user.setStatus(status);
            return userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("冻结用户", e);
            return 0;
        }
    }

    @RequestMapping("updateGrantUser")
    @ResponseBody
    public int updateGrantUser(HttpSession session, HttpServletRequest request, int userId, int status) {
        try {
            User user = new User();
            user.setId(userId);
            user.setChangeStatus(status);
            if (status == 0) {
                User getUser = userMapper.selectByPrimaryKey(userId);
                user.setType(getUser.getChangeType());
                user.setRoleId(getUser.getChangeRoleId());
                user.setLargeArea(getUser.getChangeLargeArea());
                user.setProvince(getUser.getChangeProvince());
                user.setCity(getUser.getChangeCity());
                user.setStoreNumber(getUser.getChangeStoreNumber());
                // user.setFirm(getUser.getChangeFirm());

            }
            return userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("冻结用户", e);
            return 0;
        }
    }

    @RequestMapping("updatePass")
    @ResponseBody
    public int updatePass(HttpSession session, HttpServletRequest request, String pass, String oldPass) {
        int ret = 0;
        try {
            User user = new User();

            User retUser = (User) session.getAttribute("user");
            User getUser = userMapper.getUserById(retUser.getId());

            if (!getUser.getPass().equals(Md5Util.getMd5("MD5", 0, null, oldPass))) {
                return -2;
            }
            user.setPass(Md5Util.getMd5("MD5", 0, null, pass));
            user.setId(retUser.getId());
            ret = userMapper.updateByPrimaryKeySelective(user);
            return ret;
        } catch (Exception e) {
            log.error("修改密码异常", e);
            return ret;
        }
    }

    @RequestMapping("restartPass")
    @ResponseBody
    public int restartPass(HttpSession session, HttpServletRequest request, int userId) {
        int ret = 0;
        try {
            User user = new User();

            user.setPass(Md5Util.getMd5("MD5", 0, null, "123456"));
            user.setId(userId);
            ret = userMapper.updateByPrimaryKeySelective(user);
            return ret;
        } catch (Exception e) {
            log.error("修改密码异常", e);
            return ret;
        }
    }

    @RequestMapping("updateUserById")
    @ResponseBody
    public int updateUserById(HttpSession session, User user) {
        int i = 0;
        try {
            User retUser = (User) session.getAttribute("user");
            user.setId(retUser.getId());
            i = userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("更新用户", e);
        }
        return i;
    }

    @RequestMapping("updateUser")
    public void updateUser(HttpSession session, HttpServletRequest request, User user) {
        try {

            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("冻结用户", e);
        }
    }

    // @RequestMapping("updatePass")
    // @ResponseBody
    // public int updatePass(HttpSession session,HttpServletRequest
    // request,String oldPass,String ) {
    // try {
    // User user=new User();
    // user.setId(userId);
    // user.setStatus(status);
    // return userMapper.updateByPrimaryKeySelective(user);
    // } catch (Exception e) {
    // log.error("冻结用户", e);
    // return 0;
    // }
    // }*/
}
