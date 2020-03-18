package com.weirdo.server.thread;


import com.weirdo.model.entity.Notice;
import com.weirdo.model.entity.User;
import com.weirdo.server.service.EmailService;

import java.util.concurrent.Callable;

/**
 * 发送通告到商户的thread
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2019/10/30 15:24
 **/
public class NoticeThread implements Callable<Boolean>{

    private User user;

    private Notice notice;

    private EmailService emailService;

    public NoticeThread(User user, Notice notice, EmailService emailService) {
        this.user = user;
        this.notice = notice;
        this.emailService = emailService;
    }

    /**
     * 调用邮件服务 TODO:写法二-线程池/多线程触发
     * @return
     * @throws Exception
     */
    @Override
    public Boolean call() throws Exception {
        emailService.emailUserNotice(notice,user);
        return true;
    }
}

































