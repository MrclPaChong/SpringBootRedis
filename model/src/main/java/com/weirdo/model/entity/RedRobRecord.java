package com.weirdo.model.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抢红包记录(RedRobRecord)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Data
@ToString
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
    private BigDecimal amount;
    /**
    * 时间
    */
    private Date robTime;
    
    private Byte isActive=1;


}