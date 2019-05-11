package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Dictionary;
import com.monco.core.entity.MaterialOrder;
import com.monco.core.entity.User;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.MaterialOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("material-order")
public class MaterialOrderController {

    @Autowired
    MaterialOrderService materialOrderService;

    @PostMapping
    public ApiResult save(@RequestBody MaterialOrder materialOrder) {
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        MaterialOrder materialOrder = materialOrderService.find(id);
        return ApiResult.ok(materialOrder);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          MaterialOrder materialOrder, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 材料名称
        if (StringUtils.isNotBlank(materialOrder.getMaterialName())) {
            queryParam.setFiled("materialName").setMatchType(MatchType.equal).setValue(materialOrder.getMaterialName());
            params.add(queryParam);
        }
        Page<MaterialOrder> result = materialOrderService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }
}
