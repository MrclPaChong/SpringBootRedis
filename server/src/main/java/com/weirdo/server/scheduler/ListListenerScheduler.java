package com.weirdo.server.scheduler;

import com.google.common.collect.Lists;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.UserDao;
import com.weirdo.model.entity.Notice;
import com.weirdo.model.entity.User;
import com.weirdo.server.service.EmailService;
import com.weirdo.server.thread.NoticeThread;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ListListenerScheduler
 * @Author: 86166
 * @Date: 2020/3/17 21:05
 * @Description: chenLei
 * List消费者定时器
 */
@Component
@EnableScheduling
public class ListListenerScheduler {

    private static final Logger log = LoggerFactory.getLogger(ListListenerScheduler.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;

    /**
     * 近实时的定时任务:10秒
     */
    @Scheduled(cron = "0/59 * * * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void schedulerListenNotice(){

        log.info("定时任务调度(schedulerListenNotice)：{监听、检测、监听List 的通告信息}");

        ListOperations<String, Notice> listOperations = redisTemplate.opsForList();
        Notice notice = listOperations.rightPop(Constant.RedisListNoticeKey);
        while (notice!=null){
            //TODO 发送给所有邮箱
            this.noticeUser(notice);
            notice = listOperations.rightPop(Constant.RedisListNoticeKey);
        }
    }

    /**
     * 异步任务调度配置在 config目录下
     * @param notice
     */
    @Async("threadPoolTaskExecutor")
    private void noticeUser(Notice notice){
        if (notice!=null){
            List<User> list = userDao.queryAll(null);

           /* //TODO 写法一 java8 stream api触发  传统的资源消耗高
            if(list!=null && list.isEmpty()){
                list.forEach(user -> emailService.emailUserNotice(notice,user));
            }*/

            /**
             * FixThreadPool 定长线程池，
             * CachedThreadPool 缓存线程池，
             * ScheduledThreadPool 定时线程池，
             * sSingleThreadPool单线程的线程池
             */
            //TODO:写法二-线程池/多线程触发     节省资源
            try {
                if (list!=null && !list.isEmpty()){
                    //定长任务线程
                    ExecutorService executorService=Executors.newFixedThreadPool(4);
                    List<NoticeThread> threads= Lists.newLinkedList();

                    list.forEach(user -> {
                        threads.add(new NoticeThread(user,notice,emailService));
                    });

                    executorService.invokeAll(threads);
                }
            }catch (Exception e){
                log.error("近实时的定时任务检测-发送通知给到不同的商户-法二-线程池/多线程触发-发生异常：",e.fillInStackTrace());
            }
        }
    }
}
