package com.TheCoderKushagra.Invoice_Generator.RedisCache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OtpCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, String value, long timeToLive){
        try{
            redisTemplate.opsForValue().set(key,value,timeToLive, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception",e);
        }
    }

    public String get(String key){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o != null){
                return o.toString();
            }
            return null;
        } catch (Exception e) {
            log.error("Exception", e);
            return null;
        }
    }
}
