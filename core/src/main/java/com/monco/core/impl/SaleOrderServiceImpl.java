package com.monco.core.impl;

import com.monco.core.dao.SaleOrderDao;
import com.monco.core.entity.Product;
import com.monco.core.entity.SaleOrder;
import com.monco.core.service.ProductService;
import com.monco.core.service.SaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: monco
 * @Date: 2019/5/15 22:30
 * @Description:
 */
@Service
public class SaleOrderServiceImpl extends BaseServiceImpl<SaleOrder, Long> implements SaleOrderService {

    @Autowired
    SaleOrderDao saleOrderDao;

    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public boolean saveSaleOrder(SaleOrder saleOrder) {
        Product product = productService.find(saleOrder.getProductId());
        if (product.getNumber() < saleOrder.getNumber()) {
            return false;
        }
        product.setNumber(product.getNumber() - saleOrder.getNumber());
        productService.save(product);
        this.save(saleOrder);
        return true;
    }
}
