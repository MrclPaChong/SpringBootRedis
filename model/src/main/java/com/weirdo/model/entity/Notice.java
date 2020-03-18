package com.weirdo.model.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 通告(Notice)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:08:25
 */
@Data
public class Notice implements Serializable {
    private static final long serialVersionUID = 158673910103716887L;
    
    private Integer id;
    /**
    * 通告标题
    */
    @NotBlank(message = "通告标题不能为空")
    private String title;
    /**
    * 内容
    */
    @NotBlank(message = "通告内容不能为空")
    private String content;
    
    private Object isActive=1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

}