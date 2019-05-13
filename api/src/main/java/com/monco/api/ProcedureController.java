package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Dictionary;
import com.monco.core.entity.Material;
import com.monco.core.entity.Procedure;
import com.monco.core.page.PageResult;
import com.monco.core.page.ProcedurePage;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.ProcedureService;
import lombok.extern.slf4j.Slf4j;
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
 * @Date: 2019/5/12 20:22
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("procedure")
public class ProcedureController {

    @Autowired
    ProcedureService procedureService;

    @PostMapping
    public ApiResult save(@RequestBody ProcedurePage procedurePage) {
        Procedure procedure = new Procedure();
        pageToEntity(procedurePage, procedure);
        if (procedurePage.getParentId() == null) {
            procedure.setParentId(0L);
        }
        List<Material> materialList = procedurePage.getMaterialList();
        procedureService.saveProcedure(procedure, materialList);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Procedure procedure = procedureService.find(id);
        procedure.setDataDelete(ConstantUtils.DELETE);
        procedureService.save(procedure);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        Procedure procedure = procedureService.find(id);
        ProcedurePage procedurePage = new ProcedurePage();
        entityToPage(procedure, procedurePage);
        return ApiResult.ok(procedurePage);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          ProcedurePage procedurePage, OrderQuery orderQuery) {
        if (pageSize == 0) {
            Procedure procedure = new Procedure();
            procedure.setDataDelete(ConstantUtils.UN_DELETE);
            Example<Procedure> procedureExample = Example.of(procedure);
            List<Procedure> procedureList = procedureService.findAll(procedureExample, Sort.by("id"));
            List<ProcedurePage> procedurePageList = new ArrayList<>();
            for (Procedure entity : procedureList) {
                ProcedurePage page = new ProcedurePage();
                entityToPage(entity, page);
                procedurePageList.add(page);
            }
            return ApiResult.ok(procedurePageList);
        }
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        if (StringUtils.isNotBlank(procedurePage.getProcedureName())) {
            queryParam = new QueryParam("procedureName", MatchType.like, procedurePage.getProcedureName());
            params.add(queryParam);
        }
        Page<Procedure> result = procedureService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        List<Procedure> procedureList = result.getContent();
        List<ProcedurePage> procedurePageList = new ArrayList<>();
        for (Procedure entity : procedureList) {
            ProcedurePage page = new ProcedurePage();
            entityToPage(entity, page);
            procedurePageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), procedurePageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(Procedure procedure, ProcedurePage procedurePage) {
        BeanUtils.copyProperties(procedure, procedurePage);
        procedurePage.setId(procedure.getId());
        List<Material> materialList = procedureService.getMaterialList(procedure.getId());
        procedurePage.setMaterialList(materialList);
    }

    public void pageToEntity(ProcedurePage procedurePage, Procedure procedure) {
        BeanUtils.copyProperties(procedurePage, procedure);
    }
}
