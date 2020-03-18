package com.weirdo.easycode.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 商品信息表(Item)实体类
 *
 * @author makejava
 * @since 2020-03-16 16:59:25
 */
public class Item implements Serializable {
    private static final long serialVersionUID = -54203633997331032L;
    
    private Integer id;
    /**
    * 商品编号
    */
    private String code;
    /**
    * 商品名称
    */
    private String name;
    
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}