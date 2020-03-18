package com.weirdo.server.redis;




import com.weirdo.api.response.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @ClassName: StringRedisService
 * @Author: 86166
 * @Date: 2020/3/17 10:55
 * @Description: chenLei
 */
@Service
public class StringRedisService {

    //引入日志
    private static final Logger log = LoggerFactory.getLogger(StringRedisService.class);

    //注入StringRedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建储存/读取 对象
     * @return
     */
    private ValueOperations getOperation(){
        return stringRedisTemplate.opsForValue();
    }



    /**
     * 储存redis
     * @param key
     * @param value
     * @throws Exception
     */
    public void put(final String key,final String value) throws Exception{

        //RedisStringPrefix 自定义Redis KEY+变量key
        getOperation().set(Constant.RedisStringPrefix+key,value);
    }

    /**
     * 根据Redis key 获取对应的对象参数
     * @param key
     * @return
     * @throws Exception
     */
    public Object get(final String key) throws Exception{
        return getOperation().get(Constant.RedisStringPrefix+key);
    }

    /**
     * 根据KEY 判断Redis是否存在缓存数据
     * @param key
     * @return
     * @throws Exception
     */
    public Boolean exist(final String key) throws Exception{
        return stringRedisTemplate.hasKey(Constant.RedisStringPrefix+key);
    }

    /**
     * 根据KEY expire(存活时间)判断Redis是否存在缓存数据
     * @param key
     * @param expireSeconds
     * @throws Exception
     */
    public void expire(final String key,final Long expireSeconds) throws Exception{
        stringRedisTemplate.expire(key,expireSeconds, TimeUnit.SECONDS);
    }
}
