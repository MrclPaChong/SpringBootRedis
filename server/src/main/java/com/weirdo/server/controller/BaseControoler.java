package com.weirdo.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: BaseControoler
 * @Author: 86166
 * @Date: 2020/3/16 18:23
 * @Description: chenLei
 */
@RestController
@RequestMapping("base")
public class BaseControoler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String RedisHelloWorldKey="SpringBootRedis:HelloWorld";

    @GetMapping(value = "info")
    public Map<String,Object> info(){

       Map myMap = new HashMap<String, Object>();
        String name = "Redis 技术入门及抢红包场景";
        int code = 10010;
        myMap.put("name",name);
        myMap.put("code",code);

        return myMap;
    }

    @PostMapping(value = "hello/world/set")
    public String helloWorldSet(@RequestParam String helloWorld) {

        //存数据进redis
        stringRedisTemplate.opsForValue().set(RedisHelloWorldKey,helloWorld);

        return "helloWorld";
    }

    @GetMapping(value = "hello/world/{id}")
    public String helloWorldGet(@PathVariable("id") Integer id) {

        System.out.println(id);

        //取出以：RedisHelloWorldKey 存的数据
        return stringRedisTemplate.opsForValue().get(RedisHelloWorldKey);
    }
}
