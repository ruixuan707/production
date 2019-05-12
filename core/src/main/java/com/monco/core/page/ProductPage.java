package com.monco.core.page;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/12 21:45
 * @Description:
 */
@Data
public class ProductPage extends BasePage {

    private static final long serialVersionUID = 6047115847278129170L;

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
    private Long[] procedure;

    /**
     * 工序步骤
     */
    private Integer procedureStep;
}
