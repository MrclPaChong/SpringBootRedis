package com.weirdo.server.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.ItemDao;
import com.weirdo.model.entity.Item;
import com.weirdo.server.redis.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 典型应用场景实战
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2019/11/1 11:44
 **/
@Service
public class CacheFightService {

    private static final Logger log= LoggerFactory.getLogger(CacheFightService.class);

    //雪花算法:SnowFlake算法生成id的结果是一个64bit大小的整数=41bit-时间戳+10bit-工作机器id+12bit-序列号
    private static final Snowflake SNOWFLAKE=new Snowflake(3,2);

    @Autowired
    private StringRedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemDao itemMapper;


    /**
     * 缓存穿透-查询商品
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItem(Integer id) throws Exception{
        Item item=null;
        if (id!=null){
            if (redisService.exist(id.toString())){
                String result=redisService.get(id.toString()).toString();
                log.info("---缓存穿透，从缓存中查询---");

                if (StrUtil.isNotBlank(result)){
                    item=objectMapper.readValue(result,Item.class);
                }
            }else{
                log.info("---缓存穿透，从数据库查询：id={}",id);

                item=itemMapper.queryById(id);
                if (item!=null){
                    redisService.put(id.toString(),objectMapper.writeValueAsString(item));
                }
            }
        }
        return item;
    }


    /**
     * 缓存穿透-查询商品-解决方法 设置key的value为""
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItemV2(Integer id) throws Exception{
        Item item=null;

        if (id!=null){
            if (redisService.exist(id.toString())){
                log.info("---缓存穿透，从Redis缓存取数据");
                String result=redisService.get(id.toString()).toString();

                if (StrUtil.isNotBlank(result)){
                    item=objectMapper.readValue(result,Item.class);
                }
            }else{
                log.info("---缓存穿透，从数据库查询：id={}",id);

                item=itemMapper.queryById(id);
                if (item!=null){
                    redisService.put(id.toString(),objectMapper.writeValueAsString(item));
                    //穿透方案一：给Redis 设置一个key的 元素为""的值
                }else{
                    log.info("穿透方案一：给Redis 设置一个key的 元素为\"\"的值");
                    redisService.put(id.toString(),"");
                    redisService.expire(id.toString(),3600L);
                }
            }
        }
        return item;
    }


    /**
     * 缓存穿透-查询商品-解决方法 - 限流
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItemV3(Integer id) throws Exception{
        Item item=null;

        if (id!=null){
            if (redisService.exist(id.toString())){
                log.info("---缓存穿透，从Redis缓存取数据");
                String result=redisService.get(id.toString()).toString();

                if (StrUtil.isNotBlank(result)){
                    item=objectMapper.readValue(result,Item.class);
                }
            }else{
                log.info("---guava提供的RateLimiter，获取到令牌-缓存穿透，从数据库查询：id={}",id);

                item=itemMapper.queryById(id);
                if (item!=null){
                    redisService.put(id.toString(),objectMapper.writeValueAsString(item));
                }else{
                    //TODO:connection reset.....
                    redisService.put(id.toString(),"");
                    redisService.expire(id.toString(),3600L);
                }

            }
        }
        return item;
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 缓存击穿-查询商品详情
     * Redis分布式锁
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItemCacheBeat(Integer id) throws Exception{
        Item item=null;

        if (redisService.exist(id.toString())){
            log.info("从Redis缓存中取数据");
            String result=redisService.get(id.toString()).toString();

            if (StrUtil.isNotBlank(result)){
                item=objectMapper.readValue(result,Item.class);
            }
        }else{
            /**
             * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值
             * Redis Expire 命令用于设置 key 的过期时间，key 过期后将不再可用。单位以秒计。
             * Redis DEL 命令用于删除已存在的键。不存在的 key 会被忽略。
             */
            //TODO:分布式锁的实现 - 同一时刻只能保证 拥有该key 的一个线程进入 执行共享的业务代码
            //TODO:SETNX、EXPIRE、DELETE ； Redis单线程（它的操作是原子性操作） - io多路复用（单核cpu 却支持多任务、多用户的使用，频繁切换很快，用户识别不了）

            //雪花算法：获取不重复的ID
            String value=SNOWFLAKE.nextIdStr();

            ValueOperations<String,String> valueOperations=stringRedisTemplate.opsForValue();
            //设置一个key，value，返回true/false
            Boolean lock=valueOperations.setIfAbsent(Constant.RedisCacheBeatLockKey,value);
            try {
                if (lock){
                    log.info("------------------进入到Redis分布式锁中------------------");
                    //设置操作时间 key设置过期时间  等待时间
                    stringRedisTemplate.expire(Constant.RedisCacheBeatLockKey,10L,TimeUnit.SECONDS);

                    log.info("---缓存击穿，从数据库查询：id={}",id);
                    item=itemMapper.queryById(id);
                    if (item!=null){
                        redisService.put(id.toString(),objectMapper.writeValueAsString(item));
                    }else{
                        //TODO:connection reset.....
                        redisService.put(id.toString(),"");
                        redisService.expire(id.toString(),3600L);
                    }

                }
            }finally {
                //从Redis缓存获取雪花算法的ID ，如果获取的ID不为null 并且 与 生成的ID相同，删除缓存中分布式锁ID
                String currValue=valueOperations.get(Constant.RedisCacheBeatLockKey);
                if (StrUtil.isNotBlank(currValue) && currValue.equals(value)){
                    log.info("拿到数据删除Redis缓存分布式锁ID");
                    stringRedisTemplate.delete(Constant.RedisCacheBeatLockKey);
                }
            }

        }
        return item;
    }

}
































