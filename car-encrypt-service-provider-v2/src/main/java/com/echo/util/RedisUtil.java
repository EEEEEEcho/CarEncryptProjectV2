package com.echo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void hset(String key,String subKey,String subValue){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        try{
            hash.put(key,subKey,subValue);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void hmset(String key, Map<String,String> map){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        try {
            hash.putAll(key,map);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void del(String key){
        try{
            redisTemplate.delete(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String hget(String key,String field){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        try{
            return (String) hash.get(key,field);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void hdel(String key,String filed){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        try{
            hash.delete(key,filed);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Map<Object, Object> hgetall(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        try{
            return hash.entries(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
