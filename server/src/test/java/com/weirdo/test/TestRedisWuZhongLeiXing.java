package com.weirdo.test;

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

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
        final String key1 = "SpringBootRedis:testString1:";
        final String key2 = "SpringBootRedis:testString2:";
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
        final String key = "SpringBootRedis:testList:";

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
        final  String key1 ="SpringBoot:Redis:testSet:10001";
        final  String key2 ="SpringBoot:Redis:testSet:10002";
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
        final  String key ="SpringBoot:Redis:testZset:";
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

        System.out.println("ssssssssssssssssssssssssssssssssss");
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
}
