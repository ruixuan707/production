package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.common.bean.RandomUtils;
import com.monco.core.entity.*;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.BusinessService;
import com.monco.core.service.MaterialOrderService;
import com.monco.core.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("material-order")
public class MaterialOrderController {

    @Autowired
    MaterialService materialService;

    @Autowired
    MaterialOrderService materialOrderService;

    @Autowired
    BusinessService businessService;

    @PostMapping
    public ApiResult save(@RequestBody MaterialOrder materialOrder) {
        transfer(materialOrder);
        materialOrderService.saveMaterialOrder(materialOrder);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        MaterialOrder materialOrder = materialOrderService.find(id);
        materialOrder.setDataDelete(ConstantUtils.DELETE);
        materialOrderService.save(materialOrder);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        MaterialOrder materialOrder = materialOrderService.find(id);
        return ApiResult.ok(materialOrder);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          MaterialOrder materialOrder, OrderQuery orderQuery) {
        if (pageSize == 0) {
            MaterialOrder validate = new MaterialOrder();
            validate.setDataDelete(ConstantUtils.UN_DELETE);
            Example<MaterialOrder> materialExample = Example.of(validate);
            return ApiResult.ok(materialOrderService.findAll(materialExample, Sort.by("id")));
        }
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 订单号
        if (StringUtils.isNotBlank(materialOrder.getOrderNo())) {
            queryParam.setFiled("orderNo").setMatchType(MatchType.like).setValue(materialOrder.getOrderNo());
            params.add(queryParam);
        }
        // 商家名称
        if (StringUtils.isNotBlank(materialOrder.getBusinessName())) {
            queryParam.setFiled("businessName").setMatchType(MatchType.like).setValue(materialOrder.getBusinessName());
            params.add(queryParam);
        }
        // 材料名称
        if (StringUtils.isNotBlank(materialOrder.getMaterialName())) {
            queryParam.setFiled("materialName").setMatchType(MatchType.equal).setValue(materialOrder.getMaterialName());
            params.add(queryParam);
        }
        Page<MaterialOrder> result = materialOrderService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    public void transfer(MaterialOrder materialOrder) {
        // 绑定材料
        Material material = materialService.find(materialOrder.getMaterialId());
        materialOrder.setMaterialName(material.getMaterialName());
        materialOrder.setMaterialNorms(material.getMaterialNorms());
        materialOrder.setMaterialLevel(material.getMaterialLevel());
        materialOrder.setPrice(material.getPrice());
        materialOrder.setTotalCost(material.getPrice().multiply(new BigDecimal(materialOrder.getNumber())));
        materialOrder.setOrderNo(RandomUtils.orderNo());
        // 绑定商家
        Business business = businessService.find(materialOrder.getBusinessId());
        materialOrder.setBusinessName(business.getBusinessName());
    }
}
