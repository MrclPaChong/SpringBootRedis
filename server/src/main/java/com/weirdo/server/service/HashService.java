package com.weirdo.server.service;

import com.weirdo.model.dao.SysConfigDao;
import com.weirdo.model.entity.SysConfig;
import com.weirdo.server.redis.HashRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HashService
 * @Author: 86166
 * @Date: 2020/3/19 14:37
 * @Description: chenLei
 */
@Service
public class HashService {

    private static final Logger log= LoggerFactory.getLogger(HashService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private HashRedisService hashRedisService;


    /**
     * 添加字典配置表
     * @param sysConfig
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysConfig(SysConfig sysConfig) throws Exception{

       int res = sysConfigDao.insert(sysConfig);
        if (res>0){
            //触发Redis Hash存储
            hashRedisService.cacheDbSysConfigMap();

        }
        return sysConfig.getId();
    }

    /**
     * 取出缓存中所有的数据字典列表
     * @return
     * @throws Exception
     */
    public Map<String, List<SysConfig>> getAll() throws Exception{
        return hashRedisService.getAllCacheConfig();
    }

    /**
     * 取出缓存中特定的数据字典列表
     * @param type
     * @return
     * @throws Exception
     */
    public List<SysConfig> getByType(final String type) throws Exception{
        return hashRedisService.getCacheConfigByType(type);
    }
}
