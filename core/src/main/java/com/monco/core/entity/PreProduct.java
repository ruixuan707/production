package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/13 16:20
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "sys_pre_product")
public class PreProduct extends BaseEntity<Long> {

    private static final long serialVersionUID = 7152395154953998506L;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型
     */
    private Integer productType;

    /**
     * 材料类型
     */
    private String productLevel;

    /**
     * 材料规格
     */
    private String productNorms;

    /**
     * 生产数量
     */
    private Long number;

    /**
     * 绑定工序
     */
    private String procedureIds;

    /**
     * 工序步骤
     */
    private Integer procedureStep;

    /**
     * 生产工序
     */
    private Long buildProcedure;
}
