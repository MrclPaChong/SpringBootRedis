package com.weirdo.server.controller;

import cn.hutool.core.util.StrUtil;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.entity.SysConfig;
import com.weirdo.server.service.HashService;
import com.weirdo.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: HashController
 * @Author: 86166
 * @Date: 2020/3/19 14:32
 * @Description: chenLei
 */
@RestController
@RequestMapping("hash")
public class HashController extends AbstractController{

    @Autowired
    private HashService hashService;

    /**
     *
     * @param sysConfig
     * @param result
     * @return
     */
    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse put(@RequestBody @Validated SysConfig sysConfig, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("获取系统字典表：{}",sysConfig);
            hashService.addSysConfig(sysConfig);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 获取所有系统字段表
     * @return
     */
    @GetMapping("get")
    public BaseResponse get(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(hashService.getAll());

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 获取指定类型系统字典表
     * @param type
     * @return
     */
    @RequestMapping(value = "get/type",method = RequestMethod.GET)
    public BaseResponse getType(@RequestParam String type){

        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(hashService.getByType(type));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}

