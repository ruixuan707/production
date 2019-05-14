package com.monco.core.page;

import lombok.Data;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/13 16:34
 * @Description:
 */
@Data
public class PreProductPage extends BasePage {

    private static final long serialVersionUID = -3612973491004174067L;

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
    private Long[] procedureIds;

    /**
     * 工序名称
     */
    private List<String> procedureName;

    /**
     * 工序步骤
     */
    private Integer procedureStep;

    /**
     * 工序
     */
    private Long buildProcedure;

    /**
     * 下一道工序名字
     */
    private String nextProcedure;
}
