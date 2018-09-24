package com.bigdatan.framework.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig {
	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	
	@Autowired
	RedisProperties redis;
	
	/**
     * JedisPoolConfig 连接池
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
    	JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
		 // 最大空闲数
		if(redis.getMaxIdle()>0) {
			jedisPoolConfig.setMaxIdle(redis.getMaxIdle());
			logger.info("setMaxIdle --->"+redis.getMaxIdle());
		}
       // 连接池的最大数据库连接数
		if(redis.getMaxTotal()>0) {
			jedisPoolConfig.setMaxTotal(redis.getMaxTotal());
			logger.info("setMaxTotal --->"+redis.getMaxTotal());
		}
       // 最大建立连接等待时间
		if(redis.getMaxWaitMillis()!=null&&redis.getMaxWaitMillis()>0 ) {
			 jedisPoolConfig.setMaxWaitMillis(redis.getMaxWaitMillis());
			 logger.info("setMaxWaitMillis --->"+redis.getMaxWaitMillis());
		}
       // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		if(redis.getMinEvictableIdleTimeMillis()>0) {
			jedisPoolConfig.setMinEvictableIdleTimeMillis(redis.getMinEvictableIdleTimeMillis());
			logger.info("setMinEvictableIdleTimeMillis --->"+redis.getMinEvictableIdleTimeMillis());
		}
       // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
       jedisPoolConfig.setNumTestsPerEvictionRun(redis.getNumTestsPerEvictionRun());
       logger.info("setNumTestsPerEvictionRun --->"+redis.getNumTestsPerEvictionRun());
       // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
       jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redis.getTimeBetweenEvictionRunsMillis());
       logger.info("setTimeBetweenEvictionRunsMillis --->"+redis.getTimeBetweenEvictionRunsMillis());
       // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
       jedisPoolConfig.setTestOnBorrow(redis.isTestOnBorrow());
       logger.info("isTestOnBorrow --->"+redis.isTestOnBorrow());
       // 在空闲时检查有效性, 默认false
       jedisPoolConfig.setTestWhileIdle(redis.isTestWhileIdle());
       logger.info("isTestWhileIdle --->"+redis.isTestWhileIdle());
		
		return jedisPoolConfig;
    }
    /**
     * 单机版配置
    * @Title: JedisConnectionFactory 
    * @param @param jedisPoolConfig
    * @param @return
    * @return JedisConnectionFactory
    * @autor lpl
    * @date 2018年2月24日
    * @throws
     */
    @Bean
    public JedisConnectionFactory  JedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        //连接池  
        JedisConnectionFactory.setPoolConfig(jedisPoolConfig);  
        //IP地址  
        JedisConnectionFactory.setHostName(redis.getHostName());  
        logger.info("setHostName---->"+redis.getHostName());
        //端口号  
        JedisConnectionFactory.setPort(redis.getPort());  
        logger.info("setPort---->"+redis.getPort());
        //如果Redis设置有密码  
        //JedisConnectionFactory.setPassword(password);  
        //客户端超时时间单位是毫秒  
        JedisConnectionFactory.setTimeout(redis.getTimeout()); 
        logger.info("setTimeout---->"+redis.getTimeout());
        return JedisConnectionFactory; 
    }

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }
    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     * 
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！  
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }
    /**
     * 注入封装RedisTemplate
    * @Title: redisUtil 
    * @return RedisUtil
    * @autor lpl
    * @date 2017年12月21日
    * @throws
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}