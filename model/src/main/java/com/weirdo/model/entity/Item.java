package com.weirdo.model.entity;

import java.io.Serializable;

/**
 * @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * @Setter：注解在属性上；为属性提供 setting 方法
 * @Getter：注解在属性上；为属性提供 getting 方法
 * @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 * @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 * @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */

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