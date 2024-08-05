package com.edigest.journalApp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o= redisTemplate.opsForValue().get(key);
            ObjectMapper mapper=new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }catch (Exception e){
            log.error("Exception found in redis service"+e);
            return null;
        }
    }

    public void set(String value, Object o, Long ttl){
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue=objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(value, jsonValue, ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception found in redis service"+e);

        }
    }
}
