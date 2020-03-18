package com.weirdo.server.service;

import com.google.common.collect.Lists;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.NoticeDao;
import com.weirdo.model.dao.ProductDao;
import com.weirdo.model.entity.Notice;
import com.weirdo.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @ClassName: ListService
 * @Author: 86166
 * @Date: 2020/3/17 16:03
 * @Description: chenLei
 */
@Service
public class ListService {

    //注入Redis
    @Autowired
    private RedisTemplate redisTemplate;

    //注入用户商品记录表
    @Autowired
    private ProductDao productDao;

    /**
     * 用户商品添加
     * @param product
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer insertProduct(Product product) throws Exception{
        Integer id =null;
        if (product!=null){
            product.setId(null);
            productDao.insert(product);

            id = product.getId();
            if (id>0){
                this.pushRedisService(product);
            }
        }
        return id;
    }
    //TODO 往缓存中塞消息 可以抽取到redisService里
    private void pushRedisService(final Product product) throws Exception{
        //创建Redis——List对象
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(Constant.RedisListPrefix+product.getUserId(),product);
    }

    /**
     * 获取用户商品信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Product> getProduct(final Integer userId) throws Exception{

     /*   //TODO:倒序->userID=10010 ->Rabbitmq入门与实战,Redis入门与实战,SpringBoot项目实战
        //最后添加的先出来 A->B->C   C-B-A
        List<Product> list = this.getRedisService(userId);

        //TODO:顺序->userID=10010 ->SpringBoot项目实战,Redis入门与实战,Rabbitmq入门与实战
        //A->B->C   A-B-C
        List<Product> list = Collections.reverse(this.getRedisService(userId));*/

        //TODO:弹出来移除的方式
        List<Product> list = this.getRightPop(userId);
            // 判断缓存为空，从数据库取出数据，并塞进Redis缓存

        return list;
    }
    //TODO 从缓存中取出数据
    private List<Product> getRedisService(final Integer userId) throws Exception{
        List<Product> list = Lists.newLinkedList();
        //创建Redis——List对象
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        final String key = Constant.RedisListPrefix+userId;
        list = listOperations.range(key,0L,listOperations.size(key));
        return list;
    }
    //TODO 从缓存中取出数据  右边弹出来移除的方式  A->B->C   C-B-A
    private List<Product> getRightPop(final Integer userId) throws Exception{
        List<Product> list = Lists.newLinkedList();
        //创建Redis——List对象
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        final String key = Constant.RedisListPrefix+userId;
        Product product = listOperations.rightPop(key);
        while (product!=null){
            list.add(product);

            product=listOperations.rightPop(key);
        }
        return list;
    }


    //--------------------------------------------------------------------------------

    @Autowired
    private NoticeDao noticeDao;


    /**
     *
     * @param notice
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public  void pushNotice(Notice notice) throws Exception{

        if (notice!=null){
            notice.setId(null);
            noticeDao.insert(notice);
            final Integer id = notice.getId();
            if (id>0){
                //TODO 塞入List列表中(队列) 准备被拉取异步通知至不同的商户的邮箱 - applicationEvent&Listener;Rabbitmq;jms

               ListOperations<String,Notice> listOperations = redisTemplate.opsForList();
               listOperations.leftPush(Constant.RedisListNoticeKey,notice);
            }
        }

    }


}
