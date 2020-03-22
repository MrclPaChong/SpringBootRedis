package com.weirdo.server.service;


import cn.hutool.core.lang.Snowflake;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.dao.RedDetailDao;
import com.weirdo.model.dao.RedRecordDao;
import com.weirdo.model.dao.RedRobRecordDao;
import com.weirdo.model.dto.RedPacketDto;
import com.weirdo.model.entity.RedDetail;
import com.weirdo.model.entity.RedRecord;
import com.weirdo.model.entity.RedRobRecord;
import com.weirdo.server.utils.RedPacketUtils;
import org.joda.time.DateTime;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 红包系统
 * @ClassName: RedPacketService
 * @Author: 86166
 * @Date: 2020/3/21 15:50
 * @Description: chenLei
 */
@Service
public class RedPacketService {

    //日志
    private static final Logger log = LoggerFactory.getLogger(RedPacketService.class);

    //redis key
    private static final String RedPacketKey = "SpringBootRedis:%s:%s";

    //雪花算法
    private static final Snowflake SNOWFLAKE = new Snowflake(3,1);

    //用户发红包记录
    @Autowired
    private RedRecordDao redRecordDao;

    //每个随机生成红包明细金额记录
    @Autowired
    private RedDetailDao redDetailDao;

    //抢红包记录
    @Autowired
    private RedRobRecordDao redRobRecordDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发红包
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public String handOut(RedPacketDto dto) throws Exception{
        //Total指定多少人抢  Amount指定总金额-单位为分
        if (dto.getTotal()>0 && dto.getAmount()>0){

            //生成红包全局唯一表示串
            String redKey = String.format(RedPacketKey,dto.getUserId(),SNOWFLAKE.nextIdStr());

            //生成红包随机金额列表
            List<Integer> redList = RedPacketUtils.divideRedPacket(dto.getAmount(),dto.getTotal());

            //入库
            recordRedPacket(dto,redList,redKey);
            //入缓存  多少人抢红包
            redisTemplate.opsForValue().set(redKey+":total",dto.getTotal());
            //红包总金额列表
            redisTemplate.opsForList().leftPushAll(redKey,redList);

            return redKey;
        }
        return null;

    }
    /**
     * 发红包入库
     * @param dto
     * @param list
     * @param redKey
     * @throws Exception
     */
    private void recordRedPacket(RedPacketDto dto,List<Integer> list,final String redKey) throws  Exception{

        RedRecord entity = new RedRecord();

        entity.setUserId(dto.getUserId());
        entity.setRedPacket(redKey);
        entity.setTotal(dto.getTotal());
        entity.setAmount(BigDecimal.valueOf(dto.getAmount()));
        //joda-time jar 日期处理工具
        entity.setCreateTime(DateTime.now().toDate());
        redRecordDao.insert(entity);


        list.parallelStream().forEach(redAmount -> {
            //红包明细金额
            RedDetail detail=new RedDetail();
            detail.setRecordId(entity.getId());
            detail.setAmount(BigDecimal.valueOf(redAmount));
            detail.setCreateTime(new DateTime().toDate());
            redDetailDao.insert(detail);
        });

    }





    /** v1
     * 抢红包-点 、拆/开 红包操作组成  - 金额为分
     * @param userId
     * @param redKey
     * @return
     * @throws Exception
     */
    public Integer rob(final Integer userId,final String redKey) throws Exception{

        //TODO:点 逻辑
        Boolean existRed=clickRed(redKey);
        if (existRed){
            //TODO:拆/开 逻辑

            //TODO:弹出一个随机金额
            Object value=redisTemplate.opsForList().rightPop(redKey);
            if (value!=null){

                //TODO:红包个数减一
                final String redTotalKey=redKey+":total";
                ValueOperations valueOperations=redisTemplate.opsForValue();
                Object currTotalObj=valueOperations.get(redTotalKey);

                Integer currTotal=currTotalObj!=null ? Integer.valueOf(String.valueOf(currTotalObj)) : 0;
                valueOperations.set(redTotalKey,currTotal-1);

                //TODO:入库
                final Integer realRedValue=Integer.valueOf(String.valueOf(value));
                recordRobRedPacket(userId,redKey,realRedValue);

                log.info("---当前用户抢到了红包：redKey={} userId={} redValue={} ",redKey,userId,realRedValue);
                return realRedValue;
            }
        }

        return null;
    }


    /** V2
     * 抢红包-点 、拆/开 红包操作组成  - 金额为分
     * @param userId
     * @param redKey
     * @return
     * @throws Exception
     */
    public Integer robV2(final Integer userId,final String redKey) throws Exception{
        ValueOperations valueOperations=redisTemplate.opsForValue();

        //TODO：判断当前用户是否抢过了红包
        final String redUserKey=redKey+userId+":rob";
        Object cacheRedValue=valueOperations.get(redUserKey);
        if (cacheRedValue!=null){
            return Integer.valueOf(String.valueOf(cacheRedValue));
        }

        //TODO:点 逻辑
        Boolean existRed=clickRed(redKey);
        if (existRed){
            //TODO:拆/开 逻辑

            //TODO:弹出一个随机金额
            Object value=redisTemplate.opsForList().rightPop(redKey);
            if (value!=null){

                //TODO:红包个数减一
                final String redTotalKey=redKey+":total";
                //将 key 所储存的值加上给定的浮点增量值（increment）
                valueOperations.increment(redTotalKey,-1L);

                //TODO:入库
                final Integer realRedValue=Integer.valueOf(String.valueOf(value));
                recordRobRedPacket(userId,redKey,realRedValue);

                //TODO:将当前用户抢到的红包金额塞入缓存

                valueOperations.set(redUserKey,value,24L, TimeUnit.HOURS);

                log.info("---当前用户抢到了红包：redKey={} userId={} redValue={} ",redKey,userId,realRedValue);
                return realRedValue;
            }
        }
        return null;
    }


    /**
     *  V3
     *  抢红包-点 、拆/开 红包操作组成  - 金额为分
     * @param userId
     * @param redKey
     * @return
     * @throws Exception
     */
    public Integer robV3(final Integer userId,final String redKey) throws Exception{
        ValueOperations valueOperations=redisTemplate.opsForValue();

        //TODO：判断当前用户是否抢过了红包
        final String redUserKey=redKey+userId+":rob";
        Object cacheRedValue=valueOperations.get(redUserKey);
        if (cacheRedValue!=null){
            return Integer.valueOf(String.valueOf(cacheRedValue));
        }

        //TODO:点 逻辑
        Boolean existRed=clickRed(redKey);
        if (existRed){
            //TODO:加锁-分布式锁-redis实现-：一个小的随机红包 每个人只能抢一次；一个人每次只能抢到一个小的随机红包金额 - 永远保证1:1的关系
            final String lockKey=redKey+userId+"-lock";
            Boolean lock=valueOperations.setIfAbsent(lockKey,redKey);
            try {
                if (lock){
                    redisTemplate.expire(lockKey,48L,TimeUnit.HOURS);


                    //TODO:拆/开 逻辑

                    //TODO:弹出一个随机金额
                    Object value=redisTemplate.opsForList().rightPop(redKey);
                    if (value!=null){

                        //TODO:红包个数减一
                        final String redTotalKey=redKey+":total";
                        valueOperations.increment(redTotalKey,-1L);

                        //TODO:入库
                        final Integer realRedValue=Integer.valueOf(String.valueOf(value));
                        recordRobRedPacket(userId,redKey,realRedValue);

                        //TODO:将当前用户抢到的红包金额塞入缓存
                        valueOperations.set(redUserKey,value,24L, TimeUnit.HOURS);

                        log.info("---加分布式锁-当前用户抢到了红包：redKey={} userId={} redValue={} ",redKey,userId,realRedValue);
                        return realRedValue;
                    }

                }
            }finally {
                //TODO:需要释放锁吗？ - 那是因为红包一发出去，就是一个新的key;一旦被抢完，生命周期永远终止

            }

        }
        return null;
    }





    /**
     * 判断缓存系统中红包的个数
     * @param redKey
     * @return
     * @throws Exception
     */
    private Boolean clickRed(final String redKey) throws Exception{
        Object total=redisTemplate.opsForValue().get(redKey+":total");
        if (total!=null && Integer.valueOf(String.valueOf(total))>0){
            return true;
        }
        return false;
    }

    /**
     * 记录抢到的红包明细
     * @param userId
     * @param redKey
     * @param amount
     * @throws Exception
     */
    private void recordRobRedPacket(final Integer userId,final String redKey,final Integer amount) throws Exception{
        RedRobRecord entity=new RedRobRecord();
        entity.setUserId(userId);
        entity.setRedPacket(redKey);
        entity.setAmount(BigDecimal.valueOf(amount));
        entity.setRobTime(DateTime.now().toDate());
        redRobRecordDao.insert(entity);
    }


}
