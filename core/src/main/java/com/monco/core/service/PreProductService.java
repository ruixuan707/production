package com.monco.core.service;

import com.monco.core.entity.PreProduct;

/**
 * @Auther: monco
 * @Date: 2019/5/13 16:29
 * @Description:
 */
public interface PreProductService extends BaseService<PreProduct, Long> {

    /**
     * 生产半成品
     *
     * @param preProduct
     */
    boolean buildPreProduct(PreProduct preProduct);
}
