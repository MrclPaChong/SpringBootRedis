package com.weirdo.server.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.server.service.CacheFightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 缓存典型应用场景实战
 * @ClassName: BaseControoler
 * @Author: 86166
 * @Date: 2020/3/16 18:23
 * @Description: chenLei
 */
@RestController
@RequestMapping("cache/fight")
public class CacheFightController {

    private static final Logger log= LoggerFactory.getLogger(CacheFightController.class);

    @Autowired
    private CacheFightService cacheFightService;

    //TODO:限流组件
    //单线程 1/秒放置一个令牌 1：令牌个数
    private static final RateLimiter LIMITER=RateLimiter.create(1);


    /**
     * 缓存穿透
     * @param id
     * @return
     */
    @RequestMapping(value = "through",method = RequestMethod.GET)
    public BaseResponse get(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //穿透
            //response.setData(cacheFightService.getItem(id));

            //方案一：DB查询不到，设置一个key的值为"" 塞进Redis缓存，下次直接从缓存拿，返回给前端null
            //response.setData(cacheFightService.getItemV2(id));

            //TODO:实际应用场景中 - 限流（组件-hystrix、guava提供的RateLimiter）
            /**
             * guava提供的RateLimiter 单线程 对单体项目支持良好
             *
             * 分布式羡慕需要重新考虑 如：hystrix 等组件
             *
             * tryAcquire尝试获取permit，默认超时时间是0，意思是拿不到就立即返回false
             *
             */
            //TODO:实际应用场景中 - 限流：guava提供的RateLimiter，尝试获取令牌：此处是单线程服务的限流,内部采用令牌捅算法实现
            if (LIMITER.tryAcquire(1)){ //  一次拿1个
            //设置限流时间
            //if (LIMITER.tryAcquire(10L, TimeUnit.SECONDS)){
                //log.info("压力测试---限流：guava提供的RateLimiter：10秒/次");
                response.setData(cacheFightService.getItemV3(id));
            }
        }catch (Exception e){
            log.error("--典型应用场景实战-缓存穿透-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


    /**
     * 缓存击穿
     * @param id
     * @return
     */
    @RequestMapping(value = "through/beat",method = RequestMethod.GET)
    public BaseResponse getBeat(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(cacheFightService.getItemCacheBeat(id));

        }catch (Exception e){
            log.error("--典型应用场景实战-缓存击穿-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}

















































