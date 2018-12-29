package lk.project.marketing.backend.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 16/7/19.
 *
 * @author gu pei
 */
@SuppressWarnings("unused")
@Configuration

public class RedisConfig {

    /**
     * 获取缓存管理实例
     * 
     * @return
     */
   @Bean
   RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }


    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        Map<String, Long> expires = new HashMap();
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(1200L);
        redisCacheManager.setExpires(expires);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setCachePrefix(new DefaultRedisCachePrefix("_coupon:"));
        return redisCacheManager;
    }

}
