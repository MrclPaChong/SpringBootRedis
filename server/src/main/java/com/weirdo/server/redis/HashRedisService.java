package com.weirdo.server.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.SysConfigDao;
import com.weirdo.model.entity.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HashRedisService
 * @Author: 86166
 * @Date: 2020/3/19 14:41
 * @Description: chenLei
 */
@Service
public class HashRedisService {

    private static final Logger log= LoggerFactory.getLogger(HashRedisService.class);

    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 实时获取字典配置表
     */
    @Async
    public void cacheDbSysConfigMap(){
        log.info("实时获取字典配置表");
        try {
            //取出数据库 系统字典表
            List<SysConfig> configs=sysConfigDao.selectActiveConfigs();
            if (configs!=null && !configs.isEmpty()){
                //创建map对象
                Map<String,List<SysConfig>> dataMap= Maps.newHashMap();

                //TODO:所有的数据字典列表遍历 -> 转化为 hash存储的map
                //遍历数据库字典表
                configs.forEach(config -> {
                    List<SysConfig> list=dataMap.get(config.getType());
                    if (list==null || list.isEmpty()){
                        list= Lists.newLinkedList();
                    }
                    list.add(config);
                    //存进dataMap
                    dataMap.put(config.getType(),list);
                });

                //TODO:存储到缓存hash中
                HashOperations<String,String,List<SysConfig>> hashOperations=redisTemplate.opsForHash();
                hashOperations.putAll(Constant.RedisHashKeyConfig,dataMap);
            }
        }catch (Exception e){
            log.error("实时获取所有有效的数据字典列表-转化为map-存入hash缓存中-发生异常：",e.fillInStackTrace());
        }
    }


    /**
     * 从缓存hash中获取所有的数据字典配置map
     * @return
     */
    public Map<String,List<SysConfig>> getAllCacheConfig(){
        Map<String,List<SysConfig>> map=Maps.newHashMap();
        try {
            HashOperations<String,String,List<SysConfig>> hashOperations=redisTemplate.opsForHash();
            map=hashOperations.entries(Constant.RedisHashKeyConfig);
        }catch (Exception e){
            log.error("从缓存hash中获取所有的数据字典配置map-发生异常：",e.fillInStackTrace());
        }
        return map;
    }


    /**
     * 从缓存hash中获取特定的数据字典列表
     * HGET key field
     * 获取存储在哈希表中指定字段的值。
     * @param type
     * @return
     */
    public List<SysConfig> getCacheConfigByType(final String type){
        List<SysConfig> list=Lists.newLinkedList();
        try {
            HashOperations<String,String,List<SysConfig>> hashOperations=redisTemplate.opsForHash();
            list=hashOperations.get(Constant.RedisHashKeyConfig,type);
        }catch (Exception e){
            log.error("从缓存hash中获取特定的数据字典列表-发生异常：",e.fillInStackTrace());
        }
        return list;
    }
}
