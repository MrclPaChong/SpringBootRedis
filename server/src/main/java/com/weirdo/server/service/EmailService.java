package com.weirdo.server.service;

import com.weirdo.model.entity.Notice;
import com.weirdo.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.SendFailedException;

/**
 * @ClassName: EmailService
 * @Author: 86166
 * @Date: 2020/3/17 21:19
 * @Description: chenLei
 * 邮件服务
 */
@Service
public class EmailService {

    private static final Logger log= LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    //TODO:给指定的用户发送通告
    public void emailUserNotice(Notice notice,User user){
        System.out.println(user);
        log.info("----给指定的用户：{} 发送通告：{}",user,notice);

        this.sendSimpleEmail(notice.getTitle(),notice.getContent(),user.getEmail());
    }


    //TODO:发送简单的邮件消息
    public void sendSimpleEmail(final String subject,final String content,final String ... tos){
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setSubject(subject);
            message.setText(content);
            message.setTo(tos);
            message.setFrom(env.getProperty("mail.send.from"));
            try {
                mailSender.send(message);
                log.debug("简单邮件已经发送。");
            }catch (MailSendException me){
                //捕捉到异常时，把无效的地址打印出来
                detectInvalidAddress(me);
            }
            log.info("----发送简单的邮箱完毕--->");
        }catch (Exception e){
            log.error("--发送简单的邮件消息,发生异常：",e.fillInStackTrace());
        }
    }

    /**
     * 群发的地址中，有一个或多个是无效的地址
     * Java mail SendFailedException: Invalid Addresses
     * @param me
     */
    private void detectInvalidAddress(MailSendException me) {
        Exception[] messageExceptions = me.getMessageExceptions();
        if (messageExceptions.length > 0) {
            Exception messageException = messageExceptions[0];
            if (messageException instanceof SendFailedException) {
                SendFailedException sfe = (SendFailedException) messageException;
                Address[] invalidAddresses = sfe.getInvalidAddresses();
                if(invalidAddresses != null) {
                    StringBuilder addressStr = new StringBuilder();
                    for (Address address : invalidAddresses) {
                        addressStr.append(address.toString()).append("; ");
                    }

                    log.error("发送邮件时发生异常！可能有无效的邮箱：{}", addressStr);
                    return;
                }
            }
        }
        log.error("发送邮件时发生异常！", me);
    }
}
