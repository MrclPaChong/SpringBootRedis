package com.weirdo.model.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包明细金额(RedDetail)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Data
@ToString
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
    private BigDecimal amount;
    
    private byte isActive=1;
    
    private Date createTime;

}