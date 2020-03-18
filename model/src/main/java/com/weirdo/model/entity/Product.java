package com.weirdo.model.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商户商品表(Product)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 560590814201322165L;
    
    private Integer id;
    /**
    * 商品名称
    */
    @NotBlank(message = "商品名称不能为空！")
    private String name;
    /**
    * 所属用户id
    */
    @NotNull(message = "所属用户id不能为空！")
    private Integer userId;
    /**
    * 浏览量
    */
    private Integer scanTotal=0;
    /**
    * 是否有效
    */
    private Object isActive=1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScanTotal() {
        return scanTotal;
    }

    public void setScanTotal(Integer scanTotal) {
        this.scanTotal = scanTotal;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

}