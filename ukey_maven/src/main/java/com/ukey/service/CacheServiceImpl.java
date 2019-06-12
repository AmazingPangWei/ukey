package com.ukey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ukey.util.RedisUtil;

@Service
public class CacheServiceImpl implements CacheService
{
	@Autowired
    private RedisUtil redisUtil;
    /**
     * 获取数据
     */
    @SuppressWarnings("unchecked")
    public <T> T cacheResult(String key) {
        return (T) redisUtil.getDataFromCache(key);
    }
    /**
     * 清除缓存
     */
    public void cacheRemove(String key) {
        redisUtil.clearCache(key);
    }
    /**
     * 放入缓存
     */
    public <T> void cachePut(String key, T value) {
        redisUtil.setDataToCache(key, value);
    }
}	
