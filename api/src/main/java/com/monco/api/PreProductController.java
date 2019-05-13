package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.CommonUtils;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.PreProduct;
import com.monco.core.entity.Procedure;
import com.monco.core.entity.Product;
import com.monco.core.page.PageResult;
import com.monco.core.page.PreProductPage;
import com.monco.core.page.ProcedurePage;
import com.monco.core.page.ProductPage;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.PreProductService;
import com.monco.core.service.ProcedureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/13 16:30
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("pre-product")
public class PreProductController {

    @Autowired
    PreProductService preProductService;

    @Autowired
    ProcedureService procedureService;

    @PostMapping
    public ApiResult save(@RequestBody PreProductPage preProductPage) {
        PreProduct preProduct = new PreProduct();
        pageToEntity(preProductPage, preProduct);
        boolean result = preProductService.buildPreProduct(preProduct);
        if (result) {
            return ApiResult.ok();
        } else {
            return ApiResult.error("原材料不足");
        }
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          PreProductPage preProductPage, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        Page<PreProduct> result = preProductService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        List<PreProduct> preProductList = result.getContent();
        List<PreProductPage> preProductPageList = new ArrayList<>();
        for (PreProduct entity : preProductList) {
            PreProductPage page = new PreProductPage();
            entityToPage(entity, page);
            preProductPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), preProductPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(PreProduct preProduct, PreProductPage preProductPage) {
        BeanUtils.copyProperties(preProduct, preProductPage);
        preProductPage.setId(preProduct.getId());
        if (StringUtils.isNotBlank(preProduct.getProcedureIds())) {
            Long[] procedureIds = CommonUtils.string2Long(preProduct.getProcedureIds(), ",");
            List<Procedure> procedureList = procedureService.findByIds(procedureIds);
            List<String> procedureNames = new ArrayList<>();
            for (Procedure procedure : procedureList) {
                procedureNames.add(procedure.getProcedureName());
            }
            preProductPage.setProcedureName(procedureNames);
        }
    }

    public void pageToEntity(PreProductPage preProductPage, PreProduct preProduct) {
        BeanUtils.copyProperties(preProductPage, preProduct);
        if (ArrayUtils.isNotEmpty(preProductPage.getProcedure())) {
            preProduct.setProcedureIds(CommonUtils.LongArray2String(preProductPage.getProcedure()));
        }
        if (preProduct.getProcedureStep() == null) {
            preProduct.setProcedureStep(ConstantUtils.NUM_1);
        }
    }
}
