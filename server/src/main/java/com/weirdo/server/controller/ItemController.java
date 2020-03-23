package com.weirdo.server.controller;

import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.server.service.CacheItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Cacheable、@CachePut、@CacheEvict等注解
 *
 *  @Cacheable: 获取并将给定的内容添加进缓存(缓存不存在时才添加进去)
 *  @CacheEvict：失效缓存
 *  @CachePut: 将给定的内容添加进缓存(不管缓存 存在不存在 都添加进去)
 *
 *  使用缓存注解后缓存中存在数据，直接从缓存中拿数据，不需要走数据库了。
 *
 * 商品详情controller
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2019/11/3 15:07
 **/
@RestController
@RequestMapping("item")
public class ItemController extends AbstractController{

    @Autowired
    private CacheItemService itemService;

    //获取详情-@Cacheable   和热部署devtools起冲突
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public BaseResponse info(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(itemService.getInfo(id));

        }catch (Exception e){
            log.error("--商品详情controller-详情-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //获取详情
    @RequestMapping(value = "info/v2",method = RequestMethod.GET)
    public BaseResponse infoV2(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(itemService.getInfoV2(id));

        }catch (Exception e){
            log.error("--商品详情controller-详情-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //删除
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public BaseResponse delete(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            itemService.delete(id);

        }catch (Exception e){
            log.error("--商品详情controller-删除-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}































