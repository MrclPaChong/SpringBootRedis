package com.weirdo.server.controller;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.dto.RedPacketDto;
import com.weirdo.server.service.RedPacketService;
import com.weirdo.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: RedController
 * @Author: 86166
 * @Date: 2020/3/21 15:45
 * @Description: chenLei
 */
@RestController
@RequestMapping("red/packet")
public class RedPacketController extends AbstractController{

    @Autowired
    private RedPacketService redPacketService;

    /**
     * 发红包
     * @param dto
     * @param result
     * @return
     */
    @RequestMapping(value = "hand/out",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result){

        System.out.println(dto);
        System.out.println(dto);
        System.out.println(dto);

        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            resMap.put("redKey",redPacketService.handOut(dto));
            response.setData(resMap);
        }catch (Exception e){
            log.error("发红包业务模块发生异常：dto={} ",dto,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 抢红包
     * @param userId
     * @param redKey
     * @return
     */
    @RequestMapping(value = "rob",method = RequestMethod.GET)
    public BaseResponse rob(@RequestParam Integer userId, @RequestParam String redKey){
        if (userId<=0 || StrUtil.isBlank(redKey)){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("接收参数：userId={} redKey={}",userId,redKey);
            log.info("接收参数：userId={} redKey={}",userId,redKey);

            //TODO 判断是否抢过红包了
            final String redUserKey=redKey+userId+":rob";
            Object cacheRedValue = redisTemplate.opsForValue().get(redUserKey);
            if (cacheRedValue!=null){
                //return Integer.valueOf(String.valueOf(cacheRedValue));
                return new BaseResponse(200,"您已抢过该红包，金额为：",Integer.valueOf(String.valueOf(cacheRedValue)));
            }

            // V1
//            Integer redValue=redPacketService.rob(userId,redKey);

            // v2
//            Integer redValue=redPacketService.robV2(userId,redKey);
//            if (redValue!=null){
//                response.setData(redValue);
//            }else{
//                return new BaseResponse(StatusCode.Fail.getCode(),"红包已被抢完！");
//            }

            // v3
            Integer redValue=redPacketService.robV3(userId,redKey);
            if (redValue!=null){
                response.setData(redValue);
            }else{
                return new BaseResponse(StatusCode.Fail.getCode(),"红包已被抢完！");
            }

        }catch (Exception e){
            log.error("抢红包业务模块发生异常：userId={} redKey={}",userId,redKey,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
