package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Material;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.MaterialService;
import com.monco.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/4 18:41
 * @Description: 材料Controller
 */
@Slf4j
@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    /**
     * 保存材料表
     *
     * @param material
     * @return
     */
    @PostMapping
    public ApiResult save(@RequestBody Material material) {
        if (material.getId() == null && material.getNumber() == null) {
            material.setNumber(0L);
        }
        materialService.save(material);
        return ApiResult.ok();
    }

    /**
     * 删除材料表
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Material material = materialService.find(id);
        material.setDataDelete(ConstantUtils.DELETE);
        materialService.save(material);
        return ApiResult.ok();
    }

    /**
     * 查询单个材料表
     *
     * @param id
     * @return
     */
    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        Material material = materialService.find(id);
        return ApiResult.ok(material);
    }

    /**
     * 列表查询材料表
     *
     * @param currentPage
     * @param pageSize
     * @param material
     * @param orderQuery
     * @return
     */
    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Material material, OrderQuery orderQuery) {
        if (pageSize == 0) {
            Material validate = new Material();
            validate.setDataDelete(ConstantUtils.UN_DELETE);
            Example<Material> materialExample = Example.of(validate);
            List<Material> materialList = materialService.findAll(materialExample, Sort.by("id"));
            return ApiResult.ok(materialList);
        }
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 材料等级
        if (StringUtils.isNotBlank(material.getMaterialLevel())) {
            queryParam.setFiled("materialLevel").setMatchType(MatchType.equal).setValue(material.getMaterialLevel());
            params.add(queryParam);
        }
        // 材料名称
        if (StringUtils.isNotBlank(material.getMaterialName())) {
            queryParam.setFiled("materialName").setMatchType(MatchType.like).setValue(material.getMaterialName());
            params.add(queryParam);
        }
        // 材料规格
        if (StringUtils.isNotBlank(material.getMaterialNorms())) {
            queryParam.setFiled("materialNorms").setMatchType(MatchType.like).setValue(material.getMaterialNorms());
            params.add(queryParam);
        }
        Page<Material> result = materialService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    /**
     * 按照keyWord 查询材料表
     *
     * @return
     */
    @GetMapping("all")
    public ApiResult all() {
        Material material = new Material();
        material.setDataDelete(ConstantUtils.UN_DELETE);
        Example<Material> dictionaryExample = Example.of(material);
        List<Material> materialList = materialService.findAll(dictionaryExample, Sort.by("id"));
        return ApiResult.ok(materialList);
    }

    /**
     * 导出数据
     *
     * @param material
     * @param response
     */
    @GetMapping("download")
    public void exportExcel(Material material, HttpServletResponse response) {
        material.setDataDelete(ConstantUtils.UN_DELETE);
        Example<Material> materialExample = Example.of(material);
        List<Material> materialList = materialService.findAll(materialExample, Sort.by("id"));
        ExcelUtils.exportExcel(materialList, "材料列表", "表名", Material.class, "材料列表.xls", response);
    }

    /**
     * 导入数据
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("import")
    public ApiResult importExcel(@RequestParam MultipartFile multipartFile) {
        List<Material> materialList = ExcelUtils.importExcel(multipartFile, ConstantUtils.NUM_1, ConstantUtils.NUM_1, Material.class);
        return ApiResult.ok();
    }
}
