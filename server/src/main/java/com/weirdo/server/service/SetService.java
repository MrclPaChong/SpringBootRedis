package com.weirdo.server.service;

import com.weirdo.api.response.Constant;
import com.weirdo.api.response.StatusCode;
import com.weirdo.model.dao.UserDao;
import com.weirdo.model.entity.Problem;
import com.weirdo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: SetService
 * @Author: 86166
 * @Date: 2020/3/18 14:09
 * @Description: chenLei
 */
@Service
public class SetService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProblemService problemService;


    /**
     * 用户注册
     * @param user
     * @return
     * @throws Exception
     */
    public  Integer registerUser(User user) throws  Exception{

        //判单Redis用户邮箱是否存在
        if (this.exist(user.getEmail())){
            throw new RuntimeException(StatusCode.UserEmailHasExist.getMsg());
        }
        //用户新注册数据入库
        Integer res = userDao.insert(user);
        if (res>0){
            SetOperations<String,String> setOperations = redisTemplate.opsForSet();
            setOperations.add(Constant.RedisSetKey,user.getEmail());
        }
        return user.getId();
    }

    /**
     * 判单Redis用户邮箱是否存在
     * @param email
     * @return
     * @throws Exception
     */
    private Boolean exist(final String email) throws Exception{

        //TODO 写法一
        /*SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        Boolean res = setOperations.isMember(Constant.RedisSetKey,email);
        if (res){
            return true;
        }else {
            User user = userDao.queryById2(email);
            if (user!=null){
                setOperations.add(Constant.RedisSetKey,user.getEmail());
                return true;
            }else {
                return false;
            }
        }*/
        //TODO 写法二
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        Long size = setOperations.size(Constant.RedisSetKey);
        //isMember 指定key集合中是否包含 指定元素 email
        if (size>0 &&setOperations.isMember(Constant.RedisSetKey,email)){
            return true;
        }else {
            //从数据库中取出来，塞进redis中
            User user = userDao.queryById2(email);
            if (user != null) {
                setOperations.add(Constant.RedisSetKey, user.getEmail());
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 取出缓存中已注册的用户的邮箱列表
     * @return
     * @throws Exception
     */
    public Set<String> getEmails() throws Exception{

        return redisTemplate.opsForSet().members(Constant.RedisSetKey);

    }

    /**
     * 从问题库中弹出一个随机的问题
     * @return
     * @throws Exception
     */
    public Problem getRedisRandomnEntity() throws Exception{
        return problemService.getRedisRandomnEntity();
    }

    /**
     *从缓存中获取随机的、乱序的试题列表
     * @return
     * @throws Exception
     */
    public Set<Problem> getRedisRandomnEntitys(Integer total) throws  Exception{
        return problemService.getRedisRandomnEntitys(total);
    }
}
