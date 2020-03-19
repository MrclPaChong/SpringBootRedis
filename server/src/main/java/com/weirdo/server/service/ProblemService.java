package com.weirdo.server.service;


import com.google.common.collect.Sets;
import com.weirdo.api.response.Constant;
import com.weirdo.model.dao.ProblemDao;
import com.weirdo.model.entity.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;;
import java.util.Set;

/**
 * @ClassName: ProblemService
 * @Author: 86166
 * @Date: 2020/3/18 15:07
 * @Description: chenLei
 */
@Service
public class ProblemService {

    private static final Logger log = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProblemDao problemDao;

    /**
     * @PostConstruct该注解被用来修饰一个非静态的void（）方法。
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
     * 并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行
     *
     * 项目启动拉取取出数据库的问题库，并塞进缓存(Redis) Set 集合中
     */
    @PostConstruct
    public void init(){
        this.initDBToCaChe();
    }

    /**
     * DB To Redis-Set
     */
    private void initDBToCaChe(){

        try {
            SetOperations<String,Problem> setOperations=redisTemplate.opsForSet();
            Set<Problem> set = problemDao.queryAllCache();
            if (set!=null && !set.isEmpty()){
                set.forEach(problem -> setOperations.add(Constant.RedisSetProblemKey,problem));
                set.forEach(problem -> setOperations.add(Constant.RedisSetProblemsKey,problem));
            }

        } catch (Exception e) {
            log.error("项目启动拉取出数据库中的问题库，并塞入缓存Set集合中-发生异常：",e.fillInStackTrace());
        }
    }

    /**
     * 从缓存中获取随机的问题
     * @return
     */
    public Problem getRedisRandomnEntity(){
        Problem problem = null;
        try {
            SetOperations<String,Problem> operations = redisTemplate.opsForSet();
            //获取缓存中问题库的大小
            Long size = operations.size(Constant.RedisSetProblemKey);
            //大于0 从换从取
            if (size>0){
                problem = operations.pop(Constant.RedisSetProblemKey);
                //其他 先调用initDBToCaChe() 塞进缓存 ，在重新取
            }else {
                this.initDBToCaChe();
                problem = operations.pop(Constant.RedisSetProblemKey);
            }
        } catch (Exception e) {
            log.error("从缓存中获取随机的问题-发生异常：",e.fillInStackTrace());
        }
        return problem;
    }


    /**
     *从缓存中获取随机的、乱序的试题列表
     * @return
     */
    public Set<Problem> getRedisRandomnEntitys(Integer total){
        Set<Problem> problem = Sets.newLinkedHashSet();
        try {
            //V1.0  稳定版
            SetOperations<String,Problem> operations = redisTemplate.opsForSet();
            //distinctRandomMembers() 去重在获取，防止随机取出重复的元素
            problem = operations.distinctRandomMembers(Constant.RedisSetProblemKey,total);

            //V2.0
            //SetOperations<String,Problem> setOperations=redisTemplate.opsForSet();
            //problems=setOperations.members(Constant.RedisSetProblemsKey);

            //V3.0
            //List list=Collections.shuffle(redisTemplate.opsForList().range());
        } catch (Exception e) {
        log.error("从缓存中获取随机,乱序的试题的问题-发生异常：",e.fillInStackTrace());
        }
        return problem;
    }


}
