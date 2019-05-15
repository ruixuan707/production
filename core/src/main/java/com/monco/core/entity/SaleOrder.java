package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/5/15 22:24
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "sys_sale_order")
public class SaleOrder extends BaseEntity<Long> {

    private static final long serialVersionUID = -7041937910378938489L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 产品id
     */
    private Long productId;
    private String productName;
    /**
     * 购买商id
     */
    private Long businessId;
    private String businessName;
    /**
     * 购买数量
     */
    private Long number;

    /**
     * 单件价格
     */
    private BigDecimal realPrice;
}
