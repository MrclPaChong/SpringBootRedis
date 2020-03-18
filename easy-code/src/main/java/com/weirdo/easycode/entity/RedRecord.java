package com.weirdo.easycode.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 发红包记录(RedRecord)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class RedRecord implements Serializable {
    private static final long serialVersionUID = 618127690146447615L;
    
    private Integer id;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 红包全局唯一标识串
    */
    private String redPacket;
    /**
    * 人数
    */
    private Integer total;
    /**
    * 总金额（单位为分）
    */
    private Double amount;
    
    private Object isActive;
    
    private Date createTime;


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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}