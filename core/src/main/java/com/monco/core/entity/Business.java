package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/5/12 14:45
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "sys_business")
public class Business extends BaseEntity<Long> {

    private static final long serialVersionUID = -3998414477430161244L;

    /**
     * 商家名称
     */
    private String businessName;

    /**
     * 商家地址
     */
    private String businessAddress;

    /**
     * 商家电话号码
     */
    private String businessPhoneNo;

    /**
     * 商家类型 0 供应商 1 销售商
     */
    private Integer businessType;
}
