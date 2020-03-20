package com.weirdo.server.scheduler;

import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.PhoneFareDao;
import com.weirdo.model.dto.FareDto;
import com.weirdo.model.entity.PhoneFare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: PhoneFareScheduler
 * @Author: 86166
 * @Date: 2020/3/19 11:47
 * @Description: chenLei
 */
@Component
@EnableScheduling
public class PhoneFareScheduler {

    private static final Logger log = LoggerFactory.getLogger(PhoneFareScheduler.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PhoneFareDao phoneFareDao;

    @Scheduled(cron = "0/59 * * * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void sortFareScheduler(){
        log.info("补偿手机号码充值排行榜-定时任务");
        this.cacheSortResult();
    }

    /**
     * 异步任务调度配置在 config目录下
     * @param
     */
    @Async("threadPoolTaskExecutor")
    private void cacheSortResult(){
        try {
            ZSetOperations<String,FareDto> zSetOperations=redisTemplate.opsForZSet();

            List<PhoneFare> list=phoneFareDao.getAllSortFares();
            log.info("补偿手机号码充值排行榜-定时任务，list：{}",list);
            if (list!=null && !list.isEmpty()){
                redisTemplate.delete(Constant.RedisSortedSetKey2);

                list.forEach(fare -> {
                    FareDto dto=new FareDto(fare.getPhone());
                    zSetOperations.add(Constant.RedisSortedSetKey2,dto,fare.getFare().doubleValue());
                });
            }
        }catch (Exception e){
            log.error("--补偿性手机号码充值排行榜-定时任务-发生异常：",e.fillInStackTrace());
        }
    }
}
