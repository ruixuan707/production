package com.monco.core.page;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
     * 产品等级
     */
    private String productLevel;

    /**
     * 产品规格
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
    private Long[] procedureIds;

    /**
     * 工序名称
     */
    private List<String> procedureName;

    /**
     * 工序步骤
     */
    private Integer procedureStep;
}
