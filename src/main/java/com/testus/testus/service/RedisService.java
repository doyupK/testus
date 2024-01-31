package com.testus.testus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String ,String> redisTemplate;
    private final RedisTemplate<String ,Object> redisObjectTemplate;


    public void setValues(String key, Object data) {
        ValueOperations<String, Object> values = redisObjectTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, Object data, Duration duration) {
        ValueOperations<String, Object> values = redisObjectTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public Object getValues(String key) {
        ValueOperations<String, Object> values = redisObjectTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

}
