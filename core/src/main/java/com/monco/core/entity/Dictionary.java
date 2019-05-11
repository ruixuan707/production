package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/5/4 18:16
 * @Description: 字典表
 */
@Entity
@Getter
@Setter
@Table(name = "sys_dictionary")
public class Dictionary extends BaseEntity<Long> {

    private static final long serialVersionUID = 4045382139144375141L;

    /**
     * 设置key
     */
    private String keyCode;

    /**
     * 设置value
     */
    private String keyValue;

    /**
     * 设置key地址
     */
    private String keyAddress;

    /**
     * 关键字
     */
    private String keyWord;
}
