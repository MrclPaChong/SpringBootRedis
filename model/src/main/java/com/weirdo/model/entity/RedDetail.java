package com.weirdo.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 红包明细金额(RedDetail)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class RedDetail implements Serializable {
    private static final long serialVersionUID = 297831045187701818L;
    
    private Integer id;
    /**
    * 红包记录id
    */
    private Integer recordId;
    /**
    * 每个小红包的金额（单位为分）
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
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