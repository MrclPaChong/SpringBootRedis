package com.weirdo.server.config;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: ThreadConfig
 * @Author: 86166
 * @Date: 2020/3/18 09:02
 * @Description: chenLei
 *
 */

public class ThreadConfig {

    /**
     * ThreadPoolExecutor:四种任务线程:https://blog.csdn.net/chzphoenix/article/details/78968075
     * {
     * FixThreadPool 定长线程池，
     * CachedThreadPool 缓存线程池，
     * ScheduledThreadPool 定时线程池，
     * sSingleThreadPool单线程的线程池
     * }
     */
    @Bean
    public Executor threadPoolTaskExceutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池的大小。线程池创建之后不会立即去创建线程，而是等待线程的到来。当当前执行的线程数大于改值是，线程会加入到缓冲队列；
        executor.setCorePoolSize(4);
        //线程池的最大数
        executor.setMaxPoolSize(8);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(10);
        //线程池最大等待队列
        executor.setQueueCapacity(8);
        //当前线程超过setCorePoolSize 达到setMaxPoolSize 等待执行  /可设置等待/抛弃...
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
