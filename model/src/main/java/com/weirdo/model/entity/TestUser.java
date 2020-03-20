package com.weirdo.model.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: TestUser
 * @Author: 86166
 * @Date: 2020/3/19 16:59
 * @Description: chenLei
 */
@Data
@ToString
public class TestUser implements Serializable {

    private String name;

    private String password;

    private String sex;

    private String type;
}
