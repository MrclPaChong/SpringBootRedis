package com.weirdo.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 抢红包记录(RedRobRecord)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class RedRobRecord implements Serializable {
    private static final long serialVersionUID = 238485847136811349L;
    
    private Integer id;
    /**
    * 用户账号
    */
    private Integer userId;
    /**
    * 红包标识串
    */
    private String redPacket;
    /**
    * 红包金额（单位为分）
    */
    private Double amount;
    /**
    * 时间
    */
    private Date robTime;
    
    private Object isActive;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getRobTime() {
        return robTime;
    }

    public void setRobTime(Date robTime) {
        this.robTime = robTime;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

}