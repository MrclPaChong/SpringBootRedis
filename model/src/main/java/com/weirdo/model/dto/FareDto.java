package com.weirdo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 手机号码唯一性
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FareDto implements Serializable{

    private String phone;

}