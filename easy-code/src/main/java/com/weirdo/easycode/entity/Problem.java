package com.weirdo.easycode.entity;

import java.io.Serializable;

/**
 * 问题库列表(Problem)实体类
 *
 * @author makejava
 * @since 2020-03-16 17:09:31
 */
public class Problem implements Serializable {
    private static final long serialVersionUID = 992482812961170108L;
    
    private Integer id;
    /**
    * 问题标题
    */
    private String title;
    /**
    * 选项A
    */
    private String choiceA;
    /**
    * 选项B
    */
    private String choiceB;
    /**
    * 是否有效(1=是;0=否)
    */
    private Object isActive;
    /**
    * 排序
    */
    private Object orderBy;


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

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

    public Object getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Object orderBy) {
        this.orderBy = orderBy;
    }

}