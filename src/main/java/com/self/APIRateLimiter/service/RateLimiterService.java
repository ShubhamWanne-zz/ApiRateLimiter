package com.self.APIRateLimiter.service;

import com.self.APIRateLimiter.model.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class RateLimiterService {

    private static final String REDIS_KEY = "REQUEST";
    private static final Long LIMIT = 5L;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    private HashOperations hashOperations;

    public void initHash(){
        if (Objects.isNull(hashOperations))
            hashOperations = redisTemplate.opsForHash();
    }


    public boolean handle(RequestWrapper request){
        initHash();
        Long value = (Long) hashOperations.get(REDIS_KEY, request.getUserId());
        if (Objects.nonNull(value)) {
            if (value >= LIMIT) return false;
            hashOperations.put(REDIS_KEY, request.getUserId(), value + 1);
        }
        else
            hashOperations.put(REDIS_KEY, request.getUserId(), 1L);
        return true;
    }

    public long clearCache() {
        initHash();
        long result = -1L;
        Map<String, Long> keys  =  hashOperations.entries(REDIS_KEY);
        if(Objects.nonNull(keys) && !keys.isEmpty()){
            result = hashOperations.delete(REDIS_KEY, keys.keySet().toArray(new String[0]));
            System.out.println("Deleted KEYS: "+ keys.keySet());
        }
        return result;
    }
}
