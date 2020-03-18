package com.weirdo.model.entity;


import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class User implements Serializable {
    private static final long serialVersionUID = 953578482059235100L;
    
    private Integer id;
    /**
    * 姓名
    */
    @NotBlank(message = "姓名不能为空")
    private String name;
    /**
    * 邮箱
    */
    @NotBlank(message = "邮箱不能为空")
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}