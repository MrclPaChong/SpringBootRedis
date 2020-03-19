package com.weirdo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * @Setter：注解在属性上；为属性提供 setting 方法
 * @Getter：注解在属性上；为属性提供 getting 方法
 * @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 * @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 * @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手机充值记录(PhoneFare)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:12
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PhoneFare implements Serializable {
    private static final long serialVersionUID = 240691705228227055L;
    
    private Integer id;
    /**
    * 手机号码
    */
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    /**
    * 充值金额
    */
    @NotNull(message = "充值金额不能为空")
    private BigDecimal fare;
    /**
    * 是否有效(1=是;0=否)
    */
    private Byte isActive=1;

}