package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.CommonUtils;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Material;
import com.monco.core.entity.Product;
import com.monco.core.page.ProductPage;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:17
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ApiResult save(@RequestBody ProductPage productPage) {
        Product product = new Product();
        pageToEntity(productPage, product);
        productService.save(product);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Product product = productService.find(id);
        product.setDataDelete(ConstantUtils.DELETE);
        productService.save(product);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        Product product = productService.find(id);
        return ApiResult.ok(product);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Product product, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        Page<Product> result = productService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    public void entityToPage(Product product, ProductPage productPage) {
        BeanUtils.copyProperties(product, productPage);
        productPage.setId(product.getId());
    }

    public void pageToEntity(ProductPage productPage, Product product) {
        BeanUtils.copyProperties(productPage, product);
        if (ArrayUtils.isNotEmpty(productPage.getProcedure())) {
            product.setProcedureIds(CommonUtils.LongArray2String(productPage.getProcedure()));
        }
        if (product.getProcedureStep() == null) {
            product.setProcedureStep(ConstantUtils.NUM_1);
        }
    }
}
