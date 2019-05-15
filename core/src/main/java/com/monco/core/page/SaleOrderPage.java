package com.monco.core.page;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/15 22:32
 * @Description:
 */
@Data
public class SaleOrderPage extends BasePage {

    private static final long serialVersionUID = -6400488534962123170L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

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
     * 购买商id
     */
    private Long businessId;

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
     * 购买数量
     */
    private Long number;

    /**
     * 单件价格
     */
    private BigDecimal realPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 总花费
     */
    private BigDecimal totalCost;
}
