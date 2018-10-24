package com.mall.framework.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
@EnableCaching
public class RedisAutoConfig extends CachingConfigurerSupport {
    /**
    * 自定义redis key值生成策略
    */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            System.out.println("缓存对象类名："+target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
            	 System.out.println("缓存对象："+obj);
            	 if(obj==null) {
            		 sb.append("none"); 
            		 continue;
            	 }
            	 sb.append(obj.toString());
                
            }
            return sb.toString();
        };
    }
    
    
    /**
    * 自定义CacheManager
    */
    @Bean
     public CacheManager cacheManager(RedisTemplate redisTemplate) {
         //全局redis缓存过期时间
    	RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        return redisCacheManager;
    }
}
