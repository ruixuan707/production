package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Business;
import com.monco.core.entity.Dictionary;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 14:49
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @PostMapping
    public ApiResult save(@RequestBody Business business) {
        businessService.save(business);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        Business business = businessService.find(id);
        return ApiResult.ok(business);
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Business business = businessService.find(id);
        business.setDataDelete(ConstantUtils.DELETE);
        businessService.save(business);
        return ApiResult.ok();
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Business business, OrderQuery orderQuery) {
        if (pageSize == 0) {
            Business validate = new Business();
            validate.setDataDelete(ConstantUtils.UN_DELETE);
            Example<Business> businessExample = Example.of(validate);
            List<Business> businessList = businessService.findAll(businessExample, Sort.by("id"));
            return ApiResult.ok(businessList);
        }

        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 商家名称
        if (StringUtils.isNotBlank(business.getBusinessName())) {
            queryParam = new QueryParam("businessName", MatchType.like, business.getBusinessName());
            params.add(queryParam);
        }
        // 商家地址
        if (StringUtils.isNotBlank(business.getBusinessAddress())) {
            queryParam = new QueryParam("businessAddress", MatchType.like, business.getBusinessAddress());
            params.add(queryParam);
        }
        // 商家联系方式
        if (StringUtils.isNotBlank(business.getBusinessPhoneNo())) {
            queryParam = new QueryParam("businessPhoneNo", MatchType.equal, business.getBusinessPhoneNo());
            params.add(queryParam);
        }
        Page<Business> result = businessService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

}
