package com.weirdo.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

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
    @NotBlank(message = "商品编码不能为空！")
    private String code;

    /**
    * 商品名称
    */
    @NotBlank(message = "商品名称不能为空！")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH")
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