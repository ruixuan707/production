package com.monco.core.service;

import com.monco.core.entity.MaterialOrder;

public interface MaterialOrderService extends BaseService<MaterialOrder, Long> {

    /**
     * 保存新订单
     *
     * @param materialOrder
     */
    void saveMaterialOrder(MaterialOrder materialOrder);
}
