package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.CommonUtils;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Business;
import com.monco.core.entity.Procedure;
import com.monco.core.entity.Product;
import com.monco.core.entity.SaleOrder;
import com.monco.core.page.PageResult;
import com.monco.core.page.ProductPage;
import com.monco.core.page.SaleOrderPage;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.BusinessService;
import com.monco.core.service.ProcedureService;
import com.monco.core.service.ProductService;
import com.monco.core.service.SaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/15 22:31
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("sale-order")
public class SaleOrderController {

    @Autowired
    ProductService productService;

    @Autowired
    BusinessService businessService;

    @Autowired
    SaleOrderService saleOrderService;

    @Autowired
    ProcedureService procedureService;

    @PostMapping
    public ApiResult save(@RequestBody SaleOrderPage saleOrderPage) {
        SaleOrder saleOrder = new SaleOrder();
        pageToEntity(saleOrderPage, saleOrder);
        if (saleOrderService.saveSaleOrder(saleOrder)) {
            return ApiResult.ok();
        }
        return ApiResult.error("库存不足");
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        SaleOrder saleOrder = saleOrderService.find(id);
        saleOrder.setDataDelete(ConstantUtils.DELETE);
        saleOrderService.save(saleOrder);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        SaleOrder saleOrder = saleOrderService.find(id);
        SaleOrderPage saleOrderPage = new SaleOrderPage();
        entityToPage(saleOrder, saleOrderPage);
        return ApiResult.ok(saleOrderPage);
    }


    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          SaleOrderPage saleOrderPage, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 订单号
        if (StringUtils.isNotBlank(saleOrderPage.getOrderNo())) {
            queryParam = new QueryParam("orderNo", MatchType.like, saleOrderPage.getOrderNo());
            params.add(queryParam);
        }
        // 产品名称
        if (StringUtils.isNotBlank(saleOrderPage.getProductName())) {
            queryParam = new QueryParam("productName", MatchType.like, saleOrderPage.getProductName());
            params.add(queryParam);
        }
        // 商家名称
        if (StringUtils.isNotBlank(saleOrderPage.getBusinessName())) {
            queryParam = new QueryParam("businessName", MatchType.like, saleOrderPage.getBusinessName());
            params.add(queryParam);
        }
        Page<SaleOrder> result = saleOrderService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        List<SaleOrder> saleOrderList = result.getContent();
        List<SaleOrderPage> saleOrderPageList = new ArrayList<>();
        for (SaleOrder entity : saleOrderList) {
            SaleOrderPage page = new SaleOrderPage();
            entityToPage(entity, page);
            saleOrderPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), saleOrderPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(SaleOrder saleOrder, SaleOrderPage saleOrderPage) {
        BeanUtils.copyProperties(saleOrder, saleOrderPage);
        saleOrderPage.setId(saleOrder.getId());
        if (saleOrder.getBusinessId() != null) {
            Business business = businessService.find(saleOrder.getBusinessId());
            saleOrderPage.setBusinessPhoneNo(business.getBusinessPhoneNo());
            saleOrderPage.setBusinessAddress(business.getBusinessAddress());
        }
        if (saleOrder.getProductId() != null) {
            Product product = productService.find(saleOrder.getProductId());
            saleOrderPage.setPrice(product.getPrice());
            saleOrderPage.setProductLevel(product.getProductLevel());
            saleOrderPage.setProductNorms(product.getProductNorms());
            // 获得这个产品的工序
            Long[] procedureIds = CommonUtils.string2Long(product.getProcedureIds(), ",");
            BigDecimal procedureValue = procedureService.getProcedureValue(procedureIds);
            // 总花费
            saleOrderPage.setTotalCost(procedureValue.multiply(new BigDecimal(saleOrder.getNumber())));
        }
        saleOrderPage.setTotalPrice(saleOrder.getRealPrice().multiply(new BigDecimal(saleOrder.getNumber())));
        saleOrderPage.setProfit(saleOrderPage.getTotalPrice().subtract(saleOrderPage.getTotalCost()));
    }

    public void pageToEntity(SaleOrderPage saleOrderPage, SaleOrder saleOrder) {
        BeanUtils.copyProperties(saleOrderPage, saleOrder);
        if (saleOrderPage.getBusinessId() != null) {
            saleOrder.setBusinessName(businessService.find(saleOrderPage.getBusinessId()).getBusinessName());
        }
        if (saleOrderPage.getProductId() != null) {
            saleOrder.setProductName(productService.find(saleOrderPage.getProductId()).getProductName());
            if (saleOrderPage.getRealPrice() == null) {
                saleOrder.setRealPrice(productService.find(saleOrderPage.getProductId()).getPrice());
            }
        }
    }
}
