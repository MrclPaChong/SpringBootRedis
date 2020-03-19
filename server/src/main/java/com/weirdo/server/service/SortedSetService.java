package com.weirdo.server.service;

import com.google.common.collect.Lists;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.PhoneFareDao;
import com.weirdo.model.dto.FareDto;
import com.weirdo.model.entity.PhoneFare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.KeyBoundCursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SortedSetService
 * @Author: 86166
 * @Date: 2020/3/19 10:00
 * @Description: chenLei
 */
@Service
public class SortedSetService {

    private static final Logger log = LoggerFactory.getLogger(SortedSetService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PhoneFareDao phoneFareDao;

    /**
     * 话费充值
     * @param phoneFare
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addPhoneFare(PhoneFare phoneFare) throws  Exception{

        log.info("进入话费充值service");
        int res = phoneFareDao.insert(phoneFare);
        if (res>0){
           ZSetOperations<String,PhoneFare> zSetOperations = redisTemplate.opsForZSet();
           zSetOperations.add(Constant.RedisSortedSetKey1,phoneFare,phoneFare.getFare().doubleValue());
        }
        return  phoneFare.getId();
    }

    /**
     * 话费充值V2.0
     * @param phoneFare
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addPhoneFare2(PhoneFare phoneFare) throws  Exception{

        log.info("进入话费充值service");
        int res = phoneFareDao.insert(phoneFare);
        if (res>0){
            //FareDtoba 手机唯一标识
            FareDto dto=new FareDto(phoneFare.getPhone());
            ZSetOperations<String, FareDto> zSetOperations = redisTemplate.opsForZSet();
            Double oldFare = zSetOperations.score(Constant.RedisSortedSetKey2,dto);
            if (oldFare!=null){
                //表示之前该手机已经充值过话费，需要叠加金额
                zSetOperations.incrementScore(Constant.RedisSortedSetKey2,dto,phoneFare.getFare().doubleValue());
            }else {
                zSetOperations.add(Constant.RedisSortedSetKey2,dto,phoneFare.getFare().doubleValue());
            }
        }
        return  phoneFare.getId();
    }




    /**
     * 话费排行榜
     * @param isAsc
     * @return
     */
    public Set<PhoneFare> getPhoneFareSort(final Boolean isAsc){
        //缓存中取数据
        ZSetOperations<String,PhoneFare> zSetOperations = redisTemplate.opsForZSet();
        final String key = Constant.RedisSortedSetKey1;
        final Long size = zSetOperations.size(key);
        // isAsc true正序/flase倒序
        return isAsc ? zSetOperations.range(key,0L,size) : zSetOperations.reverseRange(key,0L,size);
    }

    /**
     * 话费排行榜V2.0
     * @param
     * @return
     */
    public List<PhoneFare> getPhoneFareSort2(final Boolean isAsc){
        List<PhoneFare> list= Lists.newLinkedList();

        final String key=Constant.RedisSortedSetKey2;
        ZSetOperations<String,FareDto> zSetOperations=redisTemplate.opsForZSet();
        final Long size=zSetOperations.size(key);

        Set<ZSetOperations.TypedTuple<FareDto>> set=zSetOperations.reverseRangeWithScores(key,0L,size);

        if (set!=null && !set.isEmpty()){
            set.forEach(tuple -> {
                //数据通过PhoneFare get、set 取值 存值
                PhoneFare fare=new PhoneFare();
                fare.setPhone(tuple.getValue().getPhone());
                fare.setFare(BigDecimal.valueOf(tuple.getScore()));

                list.add(fare);
            });
        }
        return list;
    }
}
