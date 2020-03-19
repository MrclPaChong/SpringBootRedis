package com.weirdo.server.controller;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.entity.PhoneFare;
import com.weirdo.server.service.SortedSetService;
import com.weirdo.server.utils.ValidatorUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName: SortedSetController
 * @Author: 86166
 * @Date: 2020/3/19 09:57
 * @Description: chenLei
 */
@RestController
@RequestMapping("zset")
public class SortedSetController extends AbstractController{


    @Autowired
    private SortedSetService sortedSetService;

    /**
     * 话费充值
     * @param phoneFare
     * @param result
     * @return
     */
    @RequestMapping(value = "phoneFare/put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse put(@RequestBody @Validated PhoneFare phoneFare, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("----用户话费充值信息：{}",phoneFare);
            sortedSetService.addPhoneFare(phoneFare);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 话费充值V2.0
     * @param phoneFare
     * @param result
     * @return
     */
    @RequestMapping(value = "phoneFare/put2",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse put2(@RequestBody @Validated PhoneFare phoneFare, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("----用户话费充值信息：{}",phoneFare);
            sortedSetService.addPhoneFare2(phoneFare);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


    /**
     * 话费充值排行榜
     * @return
     */
    @RequestMapping(value = "phoneFare/get",method = RequestMethod.GET)
    public BaseResponse get(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            resMap.put("SortFaresASC",sortedSetService.getPhoneFareSort(true));
            resMap.put("SortFaresDESC",sortedSetService.getPhoneFareSort(false));
            response.setData(resMap);
        }catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    /**
     * 话费充值排行榜V2.0
     * @return
     */
    @RequestMapping(value = "phoneFare/get2",method = RequestMethod.GET)
    public BaseResponse get2(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            resMap.put("SortFaresASC",sortedSetService.getPhoneFareSort2(true));
            resMap.put("SortFaresDESC",sortedSetService.getPhoneFareSort2(false));
            response.setData(resMap);
        }catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }
}
