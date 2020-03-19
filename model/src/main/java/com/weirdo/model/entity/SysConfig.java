package com.weirdo.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典配置表(SysConfig)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
@Data
@ToString
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 727699506530293640L;
    
    private Integer id;
    /**
    * 字典类型
    */
    @NotBlank(message = "字典类型不能为空")
    private String type;
    /**
    * 字典名称
    */
    @NotBlank(message = "字典名称不能为空")
    private String name;
    /**
    * 选项编码
    */
    @NotBlank(message = "选项编码不能为空")
    private String code;
    /**
    * 选项取值
    */
    @NotBlank(message = "选项取值不能为空")
    private String value;
    /**
    * 排序
    */
    private Integer orderBy;
    /**
    * 是否有效(1=是;0=否)
    */
    private Object isActive=1;
    /**
    * 创建时间
    */
    private Date createTime;

}