package com.yonghui.portal.util.redis;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component("redisUtil")
public class RedisUtil {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "stringRedisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "stringRedisTemplate")
    private ZSetOperations<String, String> zsetOps;

    @Resource(name = "stringRedisTemplate")
    private HashOperations<String, String, String> hashOps;

    public void set(String key, String value) {
        valueOps.set(key, value);
    }

    public void set(String key, String value, long timeout) {
        valueOps.set(key, value, timeout);
    }

    public void set(String key, String value, long timeout, TimeUnit unit) {
        if (null == unit) {
            valueOps.set(key, value, timeout);
        } else {
            valueOps.set(key, value, timeout, unit);
        }
    }

    public String get(String key) {
        return valueOps.get(key);
    }

    public long incr(String key, long step) {
        return valueOps.increment(key, step);
    }

    public long incr(String key, String hashKey, long step) {
        return hashOps.increment(key, hashKey, step);
    }

    public void put(String key, String hashKey, String value) {
        hashOps.put(key, hashKey, value);
    }

    public void put(String key, Map<String, String> map) {
        hashOps.putAll(key, map);
    }

    public String get(String key, String hashKey) {
        return hashOps.get(key, hashKey);
    }

    public boolean hasKey(String key, String hashKey) {
        return hashOps.hasKey(key, hashKey);
    }

    public List<String> hmget(String key, List<String> hashKey) {
        return hashOps.multiGet(key, hashKey);
    }

    public List<String> hashValues(String key) {
        return hashOps.values(key);
    }

    public Set<String> hashKeys(String key) {
        return hashOps.keys(key);
    }

    /**
     * 移除Hash key-value
     *
     * @param key
     * @param hashKey
     */
    public void remove(String key, String hashKey) {
        hashOps.delete(key, hashKey);
    }

    /**
     * 获取以 xxxx字符串开头的hashKey
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String getHashByHashKey(String key, String hashKey) {
        Set<String> keys = hashOps.keys(key);
        if (null != keys && !keys.isEmpty()) {
            String hashKeyStr = null;
            Iterator<String> i = keys.iterator();
            while (i.hasNext()) {
                hashKeyStr = i.next();
                String regex = hashKey + "\\S{0,}";
                if (hashKeyStr.matches(regex)) {
                    return hashKeyStr;
                }
            }
        }
        return null;
    }

    public Map<String, String> getMap(String key) {
        Map<String, String> map = new HashMap<String, String>();
        Set<String> keys = hashOps.keys(key);
        if (null == keys || keys.isEmpty()) {
            return map;
        }
        String hashKey = null;
        Iterator<String> i = keys.iterator();
        while (i.hasNext()) {
            hashKey = i.next();
            map.put(hashKey, hashOps.get(key, hashKey));
        }
        return map;
    }

    /**
     * 入栈
     *
     * @param key
     * @param value
     * @return
     */
    public Long push(String key, String value) {
        return listOps.leftPush(key, value);
    }

    /**
     * 出栈
     *
     * @param key
     * @return
     */
    public String pop(String key) {
        return listOps.leftPop(key);
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    public Long in(String key, String value) {
        return listOps.rightPush(key, value);
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    public String out(String key) {
        return listOps.leftPop(key);
    }

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    public Long length(String key) {
        return listOps.size(key);
    }

    /**
     * 范围检索
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return listOps.range(key, start, end);
    }

    /**
     * 移除
     *
     * @param key
     * @param i
     * @param value
     */
    public void remove(String key, long i, String value) {
        listOps.remove(key, i, value);
    }

    /**
     * 检索
     *
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index) {
        return listOps.index(key, index);
    }

    /**
     * 置值
     *
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, long index, String value) {
        listOps.set(key, index, value);
    }

    /**
     * 裁剪
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, int end) {
        listOps.trim(key, start, end);
    }
}