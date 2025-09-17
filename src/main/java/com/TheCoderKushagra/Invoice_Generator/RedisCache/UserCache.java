package com.TheCoderKushagra.Invoice_Generator.RedisCache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class UserCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object value, long timeToLive){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,jsonValue,timeToLive, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception",e);
        }
    }

    public <T> T get(String key, Class<T> userClass){
        try {
            Object data = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(data.toString(), userClass);
        } catch (Exception e) {
            log.error("Exception", e);
            return null;
        }
    }

}
