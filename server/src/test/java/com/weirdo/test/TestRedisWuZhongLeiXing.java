package com.weirdo.test;

import com.google.common.collect.Maps;
import com.weirdo.server.ServerApplication;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.List;
import java.util.Set;


/**
 * @ClassName: TestRedisWuZhongLeiXing
 * @Author: 86166
 * @Date: 2020/3/17 09:34
 * @Description: chenLei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class TestRedisWuZhongLeiXing {


    private static final Logger log = LoggerFactory.getLogger(TestRedisWuZhongLeiXing.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * String测试
     */
    @Test
    public void testString() {
        log.info("----开始字符串测试");
        //设置Key
        final String key1 = "SpringBootRedis:test:String1:";
        final String key2 = "SpringBootRedis:test:String2:";
        //同一个键，值会按照最新插入的覆盖。保留最新的
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key1, "相同的键，值会覆盖，保留最后插入的");
        valueOperations.set(key1, 99);
        valueOperations.set(key1, 1000L);
        log.info("---当前key={} 对应的value={}", key1, valueOperations.get(key1));
        //给字段加增量值
        valueOperations.increment(key1, 10012L);
        log.info("---当前key={} 对应的value={}", key1, valueOperations.get(key1));

        redisTemplate.opsForValue().set(key2,"Redis下：String测试");
        log.info("---当前key={} 对应的value={}", key2,redisTemplate.opsForValue().get(key2) );

    }

    /**
     * List 测试 有序 值可重复
     * https://www.runoob.com/redis/redis-lists.html
     */
    @Test
    public void testList() {
        log.info("----开始List测试");
        final String key = "SpringBootRedis:test:List:";

        //清空key对应的List元素值
        stringRedisTemplate.delete(key);
       ListOperations<String,String> listOperations = stringRedisTemplate.opsForList();

        List<String> list = Lists.newArrayList("C","D","E");
        //LPUSH key value1 [value2](将一个或多个值插入到列表头部)  == leftPush
        listOperations.leftPush(key,"A");
        listOperations.leftPush(key,"B");
        listOperations.leftPushAll(key,list);

        log.info("当前列表元素个数：{}",listOperations.size(key));
        log.info("当前列表元素：{}",listOperations.range(key,0,10));

        log.info("当前列表从左边弹出：{}",listOperations.leftPop(key));
        log.info("当前列表下标为0的元素：{}",listOperations.index(key,0L));


        log.info("当前列表从右边弹出：{}",listOperations.rightPop(key));
        log.info("当前列表下标为0的元素：{}",listOperations.index(key,0L));

        listOperations.set(key,0L,"10086接线员");
        log.info("更新下标为0L的元素值：{}",listOperations.index(key,0L));

        listOperations.leftPush(key,"左边添加");
        //在查询一次
        log.info("当前列表元素：{}",listOperations.range(key,0,10));

        //删除List 下标0~10的元素
        listOperations.remove(key,0L,"10086接线员");
        log.info("当前列表元素：{}",listOperations.range(key,0,10));

    }


    /**
     * Set测试 无序 唯一 (值不可重复)
     */
    @Test
    public void  testSet(){

        log.info("set开始测试");
        final  String key1 ="SpringBoot:Redis:test:Set:10001";
        final  String key2 ="SpringBoot:Redis:test:Set:10002";
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);

        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add(key1,new String[]{"A","B","C"});
        setOperations.add(key2,new String[]{"B","D","E","F","G","H","I"});
        //Redis members 命令返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
        log.info("集合key1的元素：{}",setOperations.members(key1));
        log.info("集合key2的元素：{}",setOperations.members(key2));
        log.info("----------------------------------------------------------------------------------------------------");

        log.info("集合key1随机取的元素：{}",setOperations.randomMember(key1));
        log.info("集合key2随机取的元素：{}",setOperations.randomMembers(key2,3L));
        log.info("----------------------------------------------------------------------------------------------------");

        log.info("集合key1的元素个数：{}",setOperations.size(key1));
        log.info("集合key2的元素个数：{}",setOperations.size(key2));
        log.info("----------------------------------------------------------------------------------------------------");

        log.info("集合key1的元素中是否包含元素：{}",setOperations.isMember(key1,"A"));
        log.info("集合key2的元素中是否包含元素：{}",setOperations.isMember(key1,"P"));
        log.info("----------------------------------------------------------------------------------------------------");

        log.info("集合key1，key2元素的差集元素：{}", setOperations.difference(key1,key2));
        log.info("集合key1，key2元素的交集元素：{}", setOperations.intersect(key1,key2));
        log.info("集合key1，key2元素的并集元素：{}", setOperations.union(key1,key2));
        log.info("----------------------------------------------------------------------------------------------------");

        log.info("从集合key1中随机弹出一个元素：{}",setOperations.pop(key1));
        log.info("集合key1的元素：{}",setOperations.members(key1));
        log.info("将A从集合key1中移除：{}",setOperations.remove(key1,"A"));
        log.info("集合key1的元素：{}",setOperations.members(key1));
        log.info("集合key2的元素：{}",setOperations.members(key2));

    }

    /**
     * Zset 测试  Set+List 结合 有序、 元素唯一
     */
    @Test
    public void testZset(){

        log.info("开始Zset测试");
        final  String key ="SpringBoot:Redis:test:Zset:";
        redisTemplate.delete(key);

        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();

        //向有序集合添加一个或多个成员，或者更新已存在成员的分数
        zSetOperations.add(key,"A",1.0);
        zSetOperations.add(key,"B",3.0);
        zSetOperations.add(key,"C",2.0);
        zSetOperations.add(key,"D",4.0);

        log.info("有序集合Sorted Set-成员数：{}",zSetOperations.size(key));
        //正序：通过索引区间返回有序集合指定区间内的成员
        log.info("正序:有序集合Sorted Set-通过索引区间返回有序集合指定区间内的成员：{}",zSetOperations.range(key,0L,zSetOperations.size(key)));
        //倒序：
        log.info("倒序:有序集合Sorted Set-通过索引区间返回有序集合指定区间内的成员：{}",zSetOperations.reverseRange(key,0L,zSetOperations.size(key)));

        //获取有序集合的成员数
        log.info("有序集合Sorted Set的成员数：{}",zSetOperations.zCard(key));
        log.info("有序集合Sorted Set成员A的得分：{}",zSetOperations.score(key,"A"));
        log.info("有序集合Sorted Set成员B的得分：{}",zSetOperations.score(key,"B"));

        log.info("");

        log.info("正序：有序集合Sorted Set-中C的排名：{}",zSetOperations.rank(key,"C"));
        log.info("倒序：有序集合Sorted Set-中C的排名：{}",zSetOperations.reverseRank(key,"C"));

        //给指定key B 中的元素值 + 10.0
        zSetOperations.incrementScore(key,"B",10.0);
        log.info("有序集合Sorted Set成员B的得分：{}",zSetOperations.score(key,"B"));
        log.info("正序:有序集合Sorted Set-通过索引区间返回有序集合指定区间内的成员：{}",zSetOperations.range(key,0L,zSetOperations.size(key)));

        //移除
        zSetOperations.remove(key,"B");
        log.info("正序:有序集合Sorted Set-通过索引区间返回有序集合指定区间内的成员：{}",zSetOperations.range(key,0L,zSetOperations.size(key)));
        log.info("有序集合Sorted Set-取出范围内成员的分数：{}",zSetOperations.rangeByScore(key,0,3));

        Set<ZSetOperations.TypedTuple<String>> set = zSetOperations.rangeWithScores(key,0L,zSetOperations.size(key));

        //set.forEach(tuple -> log.info("--当前成员：{} 对应的分数：{}",tuple.getValue(),tuple.getScore()));
        set.forEach(new Consumer<ZSetOperations.TypedTuple<String>>() {
            @Override
            public void accept(ZSetOperations.TypedTuple<String> tuple) {
                log.info("--当前成员：{} 对应的分数：{}",tuple.getValue(),tuple.getScore());
            }
        });
    }


    /**
     * Hash 测试
     * Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
     * 1个key对应多个value
     */
    @Test
    public void testHash(){

        log.info("开始Hash 测试");
        final  String key ="SpringBoot:Redis:test:Hash:";
        redisTemplate.delete(key);

        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();

        hashOperations.put(key,"10010","chenlei");
        hashOperations.put(key,"10086","weirdo");

        Map<String,String> dataMap = Maps.newHashMap();
        dataMap.put("dataMap_9876","安其拉");
        dataMap.put("dataMap_9875","李白");
        dataMap.put("dataMap_9874","钟馗");
        hashOperations.putAll(key,dataMap);

        log.info("哈希-hash获取到的元素：{}",hashOperations.entries(key));
        log.info("哈希-hash获取到10010的元素：{}",hashOperations.get(key,"10010"));
        log.info("哈希-hash获取到所有的元素field：{}",hashOperations.keys(key));

        log.info("哈希-hash 判断dataMap_9875成员是否存在：{}",hashOperations.hasKey(key,"dataMap_9875"));
        log.info("哈希-hash 判断10086成员是否存在：{}",hashOperations.hasKey(key,"10086"));

        //只有在字段 field 不存在时，设置哈希表字段的值。
        hashOperations.putIfAbsent(key,"100AK","AK47-B");
        log.info("哈希-hash获取到的元素：{}",hashOperations.entries(key));

        //删除
        hashOperations.delete(key,"10010","dataMap_9875","dataMap_9874");
        log.info("哈希-hash获取到的元素：{}",hashOperations.entries(key));

        log.info("哈希-hash 获取个数：{}",hashOperations.size(key));

    }


    /**
     * 失效key
     * @throws Exception
     */
    @Test
    public void testExpire() throws Exception{
        log.info("----开始key失效测试");

        final String key = "SpringBootRedis:Hash:Key:Expire";
        redisTemplate.delete(key);

        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set(key,"lixiaolong",5L,TimeUnit.SECONDS);

        log.info("---取出数据：{}",valueOperations.get(key));
        Thread.sleep(5000);
        log.info("---取出数据：{}",valueOperations.get(key));

        valueOperations.set(key,"bruceLee");
        redisTemplate.expire(key,5L,TimeUnit.SECONDS);
        log.info("---取出数据：{}",valueOperations.get(key));
        Thread.sleep(5000);
        log.info("---取出数据：{}",valueOperations.get(key));

    }


    /**
     * 注解的缓存实战测试
     */
    @Test
    public void testCache() {
        log.info("----开始基于注解的缓存实战测试");

        log.info("--{}",stringRedisTemplate.opsForValue().get("SpringBootRedis:Item::1"));
    }

}
