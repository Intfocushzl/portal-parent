package com.yonghui.portal.util.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.model.global.User;
import com.yonghui.portal.service.TokenApiService;
import com.yonghui.portal.util.RandomNumString;
import com.yonghui.portal.util.StringUtils;
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
     * 创建token
     *
     * @param tokenApiLast
     * @return
     */
    public TokenApi createToken(TokenApi tokenApiLast, User user) {
        //用户首次登陆设置tokenApi，其他情况是更新tokenApi（根据tokenApiLast是否为空来判断）
       boolean isFirstFlag = true;
        if (tokenApiLast == null) {
            tokenApiLast = new TokenApi();
            tokenApiLast.setJobNumber(user.getJobNumber());
        } else {
            isFirstFlag = false;
            // 先删除上次登陆保存的token
            if (tokenApiLast != null && !StringUtils.isEmpty(tokenApiLast.getToken())) {
                redisBizUtil.removeApiToken(tokenApiLast.getToken());
            }
        }
        // 生成60位长度token，返回给客户端，以后每次请求在header里面带上
        String token = RandomNumString.createToken(user.getJobNumber(), 60);
        // 将token，和用户信息存放到redis
        Date now = new Date();
        // 设置过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        tokenApiLast.setUpdateTime(now);
        tokenApiLast.setExpireTime(expireTime);
        tokenApiLast.setToken(token);
        // 保存用户登陆信息
        redisBizUtil.setApiToken(token, JSONObject.toJSONString(tokenApiLast));
        //保存用户信息到redis
        redisBizUtil.setUserInfo(user.getJobNumber(), JSONObject.toJSONString(user));
        //首次登陆时设置，否是更新
        if(isFirstFlag){
            tokenApiService.save(tokenApiLast);
        }else{
            tokenApiService.update(tokenApiLast);
        }
        return tokenApiLast;
    }

}