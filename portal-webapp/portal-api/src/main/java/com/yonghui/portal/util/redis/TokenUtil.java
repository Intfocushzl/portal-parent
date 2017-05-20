package com.yonghui.portal.util.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.TokenApiService;
import com.yonghui.portal.util.RandomNumString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token管理
 *
 * @author 张海 2017.05.13
 */
@Component("tokenUtil")
public class TokenUtil {

    Logger log = Logger.getLogger(this.getClass());
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;
    @Reference
    private TokenApiService tokenApiService;
    @Autowired
    private RedisBizUtilApi redisBizUtil;

    /**
     * 创建token，用户首次登陆或者上次token已失效，则返回新的token
     * 用户已登陆并且token未失效，直接返回上次的token
     *
     * @param tokenApiLast
     * @return
     */
    public TokenApi createToken(TokenApi tokenApiLast, User user) {
        if (tokenApiLast == null) {
            return getNewTokenApi(user);
        } else {
            if (tokenApiLast.getExpireTime().getTime() < System.currentTimeMillis()) {
                // token已失效，重新登录，先删除上次登陆保存的token
                redisBizUtil.removeApiToken(tokenApiLast.getToken());
                tokenApiService.deleteByJobNumber(user.getJobNumber());
                return getNewTokenApi(user);
            } else {
                // token未失效，直接返回
                return tokenApiLast;
            }
        }
    }

    /**
     * 生成新的token
     *
     * @param user
     * @return
     */
    private TokenApi getNewTokenApi(User user) {
        TokenApi tokenApiNew = new TokenApi();
        // 生成60位长度token，返回给客户端，以后每次请求在header里面带上
        String token = RandomNumString.createToken(user.getJobNumber(), 60);
        // 将token，和用户信息存放到redis
        Date now = new Date();
        // 设置过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        tokenApiNew.setUpdateTime(now);
        tokenApiNew.setExpireTime(expireTime);
        tokenApiNew.setToken(token);
        tokenApiNew.setJobNumber(user.getJobNumber());
        // 保存用户token信息
        redisBizUtil.setApiToken(token, JSONObject.toJSONString(tokenApiNew));
        // 保存用户信息到redis
        redisBizUtil.setUserInfo(user.getJobNumber(), JSONObject.toJSONString(user));
        tokenApiService.save(tokenApiNew);
        // 返回新的token对象
        return tokenApiNew;
    }

}