package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:01
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "sys_procedure")
public class Procedure extends BaseEntity<Long> {

    private static final long serialVersionUID = 1348993114732497933L;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 工序上级
     */
    private Long parentId;

}
