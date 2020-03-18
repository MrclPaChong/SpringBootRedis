package com.weirdo.server.controller;



import cn.hutool.core.util.StrUtil;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.dao.ItemDao;
import com.weirdo.model.entity.Item;
import com.weirdo.server.service.StringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: StringCotroller
 * @Author: 86166
 * @Date: 2020/3/17 10:08
 * @Description: chenLei
 */
@RestController
@RequestMapping("String")
public class StringCotroller {

    //引入日志
    private static final Logger log = LoggerFactory.getLogger(StringCotroller.class);

    //注入ItemService
    @Autowired
    private StringService stringService;

    @Autowired
    private ItemDao itemDao;


    /**
     * String商品添加
     * @param item
     * @param result
     * @return
     */
    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse put(@RequestBody @Validated Item item, BindingResult result){

        if (result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("--商品信息：{}",item);

            stringService.insertItem(item);
        }catch (Exception e){
            log.error("--字符串String实战-商品详情存储-添加-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * String获取商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse get(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {

            response.setData(stringService.getItem(id));

        }catch (Exception e){
            log.error("--字符串String实战-商品详情存储-添加-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
