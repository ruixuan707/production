package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "sys_material_order")
public class MaterialOrder extends BaseEntity<Long> {

    private static final long serialVersionUID = -8585552862424258771L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 材料id
     */
    private Long materialId;

    /**
     * 材料名称
     */
    private String materialName;

    /**
     * 材料类型
     */
    private String materialLevel;

    /**
     * 材料规格
     */
    private String materialNorms;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 商家id
     */
    private Long businessId;

    /**
     * 商家姓名
     */
    private String businessName;

    /**
     * 数量
     */
    private Long number;

    /**
     * 总价值
     */
    private BigDecimal totalCost;

}
