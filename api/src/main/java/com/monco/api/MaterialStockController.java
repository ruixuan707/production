package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.entity.MaterialStock;
import com.monco.core.service.MaterialStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("material-stock")
public class MaterialStockController {

    @Autowired
    MaterialStockService materialStockService;

    @PostMapping
    public ApiResult save(@RequestBody MaterialStock materialStock) {
        materialStockService.save(materialStock);
        return ApiResult.ok();
    }
}
