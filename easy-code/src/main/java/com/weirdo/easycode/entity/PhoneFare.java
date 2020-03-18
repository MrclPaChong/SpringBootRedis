package com.weirdo.easycode.entity;

import java.io.Serializable;

/**
 * 手机充值记录(PhoneFare)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:12
 */
public class PhoneFare implements Serializable {
    private static final long serialVersionUID = 240691705228227055L;
    
    private Integer id;
    /**
    * 手机号码
    */
    private String phone;
    /**
    * 充值金额
    */
    private Double fare;
    /**
    * 是否有效(1=是;0=否)
    */
    private Object isActive;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

}