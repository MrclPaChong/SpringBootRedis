package com.weirdo.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典配置表(SysConfig)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 727699506530293640L;
    
    private Integer id;
    /**
    * 字典类型
    */
    private String type;
    /**
    * 字典名称
    */
    private String name;
    /**
    * 选项编码
    */
    private String code;
    /**
    * 选项取值
    */
    private String value;
    /**
    * 排序
    */
    private Integer orderBy;
    /**
    * 是否有效(1=是;0=否)
    */
    private Object isActive;
    /**
    * 创建时间
    */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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