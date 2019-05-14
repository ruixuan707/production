package com.monco.core.service;

import com.monco.core.entity.PreProduct;

import java.util.List;

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

    /**
     * 获取当前步骤的半成品
     *
     * @param preProduct
     * @return
     */
    List<PreProduct> getPreProduct(PreProduct preProduct);

    /**
     * 执行下一道工序
     * @param preProduct
     * @return
     */
    boolean nextProcedure(PreProduct preProduct);
}
