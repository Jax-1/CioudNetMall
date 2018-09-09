package com.bigdatan.framework.redis;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;


@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);
	
	/**
	 * Redis连接模式：
	 * ALONE：单机
	 * CLUSTER：集群
	 * SENTINEL：哨兵
	 */
	public static String ALONE="ALONE";
	public static String CLUSTER="CLUSTER";
	public static String SENTINEL="SENTINEL";
	
	
	@Autowired
	private RedisProperties redisProperties;
	
	/**
	 * JedisPoolConfig 连接池
	 * @return
	 */
	@Bean
	public JedisPoolConfig  jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
		 // 最大空闲数
		if(redisProperties.getMaxIdle()>0) {
			jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
			logger.info("setMaxIdle --->"+redisProperties.getMaxIdle());
		}
        // 连接池的最大数据库连接数
		if(redisProperties.getMaxTotal()>0) {
			jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
		}
        // 最大建立连接等待时间
		if(redisProperties.getMaxWaitMillis()>0) {
			 jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		}
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		if(redisProperties.getMinEvictableIdleTimeMillis()>0) {
			jedisPoolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMillis());
		}
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
		
		
		return jedisPoolConfig;
	}
	 /**
     * Redis集群的配置
    * @return RedisClusterConfiguration
    * @throws
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        //Set<RedisNode> clusterNodes
        String clusterNodes=redisProperties.getNodes();
        logger.info("setClusterNodes-------->"+clusterNodes);
        String[] serverArray = clusterNodes.split(",");

        Set<RedisNode> nodes = new HashSet<RedisNode>();

        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
        }

        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisProperties.getMax_redirects());

        return redisClusterConfiguration;
    }
    /**
     * 配置redis的哨兵
    * @return RedisSentinelConfiguration
    * @throws
     */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        //配置matser的名称
//        RedisNode redisNode = new RedisNode(redisProperties.getHostName(), redisProperties.getPort());
//        redisNode.setName("mymaster");
//        redisSentinelConfiguration.master(redisNode);
//        //配置redis的哨兵sentinel
//        RedisNode senRedisNode = new RedisNode(senHost1,senPort1);
//        Set<RedisNode> redisNodeSet = new HashSet<>();
//        redisNodeSet.add(senRedisNode);
//        redisSentinelConfiguration.setSentinels(redisNodeSet);
        return redisSentinelConfiguration;
    }
	/**
	 * 配置工厂
	 * @param jedisPoolConfig
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig,RedisClusterConfiguration redisClusterConfiguration,RedisSentinelConfiguration redisSentinelConfiguration) {
		JedisConnectionFactory jedisConnectionFactory = null;
		//根据模式配置工厂
		//配置单机模式，或模式为空时采用单机模式连接
		if(RedisAutoConfiguration.ALONE.equals(redisProperties.getMode())||redisProperties.getMode()==null) {
			jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
		        //连接池  
			jedisConnectionFactory.setPoolConfig(jedisPoolConfig);  
		        //IP地址  
			jedisConnectionFactory.setHostName(redisProperties.getHostName());  
		        //端口号  
			jedisConnectionFactory.setPort(redisProperties.getPort());  
		        //如果Redis设置有密码  
		        if(redisProperties.getPassword()!=null&&redisProperties.getPassword()!="") {
		        	jedisConnectionFactory.setPassword(redisProperties.getPassword());  
		        }
		        //客户端超时时间单位是毫秒  
		        jedisConnectionFactory.setTimeout(redisProperties.getTimeout());  
		        
			logger.info("redis factory:"+RedisAutoConfiguration.ALONE);
		}else if(RedisAutoConfiguration.CLUSTER.equals(redisProperties.getMode())) {//集群模式
			jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
			logger.info("redis factory:"+RedisAutoConfiguration.CLUSTER);
			
		}else if(RedisAutoConfiguration.SENTINEL.equals(redisProperties.getMode())) {//哨兵模式
			jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,jedisPoolConfig);
			logger.info("redis factory:"+RedisAutoConfiguration.SENTINEL);
			
		}
	    return jedisConnectionFactory;
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
