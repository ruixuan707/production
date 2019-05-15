package com.monco.core.service;

import com.monco.core.entity.SaleOrder;

/**
 * @Auther: monco
 * @Date: 2019/5/15 22:30
 * @Description:
 */
public interface SaleOrderService extends BaseService<SaleOrder, Long> {

    /**
     * 生产订单
     *
     * @param saleOrder
     */
    boolean saveSaleOrder(SaleOrder saleOrder);
}
