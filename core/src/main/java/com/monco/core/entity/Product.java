package com.monco.core.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "sys_product")
public class Product extends BaseEntity<Long> {

    private static final long serialVersionUID = 2785016245334194212L;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型
     */
    private Integer productType;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 材料类型
     */
    private String productLevel;

    /**
     * 材料规格
     */
    private String productNorms;

    /**
     * 批次号
     */
    private String productBatch;

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

}
