package com.weirdo.server.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.api.response.BaseResponse;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.dao.ItemDao;
import com.weirdo.model.entity.Item;
import com.weirdo.server.redis.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @ClassName: ItemService
 * @Author: 86166
 * @Date: 2020/3/17 10:46
 * @Description: chenLei
 */
@Service
public class StringService {
    private static final Logger log= LoggerFactory.getLogger(StringService.class);

    //注入Redis
    @Autowired
    private StringRedisService stringRedisService;

    //注入ItemService
    @Autowired
    private ItemDao itemDao;

    //对象序列化
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 添加商品
     * @param item
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer insertItem(Item item) throws Exception{
        item.setCreateTime(new Date());
        item.setId(null);
        itemDao.insert(item);

        Integer id=item.getId();
        //保证缓存-数据库双写的一致性
        if (id>0){
            stringRedisService.put(id.toString(),objectMapper.writeValueAsString(item));
        }
        return id;
    }

    /**
     *  获取商品
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItem(Integer id) throws Exception{
        Item item=null;
        if (id!=null){
            if (stringRedisService.exist(id.toString())){
                String result=stringRedisService.get(id.toString()).toString();
                log.info("---string数据类型，从缓存中取出来的value：{}",result);
                //判断不为空
                if (StrUtil.isNotBlank(result)){
                    //反序列化
                    item=objectMapper.readValue(result,Item.class);
                }
            }else{
                log.info("---string数据类型，从数据库查询：id={}",id);

                item=itemDao.queryById(id);
                if (item!=null){
                    stringRedisService.put(id.toString(),objectMapper.writeValueAsString(item));
                }
            }
        }
        return item;
    }

}
