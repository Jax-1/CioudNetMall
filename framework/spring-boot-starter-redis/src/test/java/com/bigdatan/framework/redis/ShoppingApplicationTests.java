package com.bigdatan.framework.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringBootConfig.class)
public class ShoppingApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisConnectionFactory factory;
    @Autowired
    RedisUtil redisUtil;
    @Test
    public void test() throws Exception {
//        List<String> list =new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("v");
//        stringRedisTemplate.opsForValue().set("abc", "测试");
//        stringRedisTemplate.opsForList().leftPushAll("qq",list); // 向redis存入List
//        stringRedisTemplate.opsForList().range("qwe",0,-1).forEach(value ->{
//           System.out.println(value);
//        });
    	 RedisConnection conn = factory.getConnection();
    	 System.out.println("1111111111111111");
    	 
         conn.set("hello".getBytes(), "world".getBytes());
         System.out.println(new String(conn.get("hello".getBytes())));
//    	redisUtil.set("user", "111111");
//    	redisUtil.get("user");
    }
}