package com.weirdo.model.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发红包记录(RedRecord)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Data
@ToString
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
    private BigDecimal amount;
    
    private Byte isActive=1;
    
    private Date createTime;

}