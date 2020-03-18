package com.weirdo.server.controller;

import cn.hutool.core.util.StrUtil;
import com.mysql.cj.util.StringUtils;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.entity.Notice;
import com.weirdo.model.entity.Product;
import com.weirdo.server.service.EmailService;
import com.weirdo.server.service.ListService;
import com.weirdo.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ListController
 * @Author: 86166
 * @Date: 2020/3/17 15:37
 * @Description: chenLei
 */
@RequestMapping("List")
@RestController
public class ListController extends AbstractController{

    @Autowired
    private ListService listService;


    /**
     * List商品添加
     * @param product
     * @param result
     * @return
     */
    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse put(@RequestBody @Validated Product product, BindingResult result){

        if (result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("商品信息：{}",product);
            listService.insertProduct(product);
        }catch (Exception e){
            log.error("--List实战-用户商品存储-添加-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * List商品添加--效验前端参数
     * @param product
     * @param result
     * @return
     */
    @RequestMapping(value = "put/check",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse putV2(@RequestBody @Validated Product product, BindingResult result){

        BaseResponse response=new BaseResponse(StatusCode.Success);

        //TODO 结合entity 字段 注解使用
        //效验参数
        String checkResult = ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkResult)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),checkResult);
        }

        return response;
    }

    /**
     * List获取商品详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse get(@RequestParam Integer userId){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("用户id：{}",userId);
            response.setData(listService.getProduct(userId));
        }catch (Exception e){
            log.error("--List实战-用户商品获取-添加-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }



    /**
     * 平台发送邮件给用户
     * @param notice
     * @return
     */
    @RequestMapping(value = "notice/put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse putNotice(@RequestBody @Validated Notice notice,BindingResult result){

        //效验参数
        String checkRes = ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("平台发送邮件给用户：{}",notice);
            listService.pushNotice(notice);
        }catch (Exception e){
            log.error("--List实战-用户商品获取-添加-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
