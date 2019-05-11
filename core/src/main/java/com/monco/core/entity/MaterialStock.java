package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "sys_material_stock")
public class MaterialStock extends BaseEntity<Long> {

    private static final long serialVersionUID = -8067938422145084150L;

    /**
     * 材料id
     */
    private Long materialId;

    /**
     * 库存类型
     */
    private Integer stockType;

    /**
     * 库存数量
     */
    private Long number;

    /**
     * 材料名称
     */
    private String materialName;

    /**
     * 材料类型
     */
    private String materialType;

    /**
     * 注册时间
     */
    private String registerDate;

}
