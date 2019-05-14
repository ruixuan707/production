package com.monco.core.impl;

import com.monco.common.bean.CommonUtils;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.*;
import com.monco.core.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/13 16:29
 * @Description:
 */
@Service
public class PreProductServiceImpl extends BaseServiceImpl<PreProduct, Long> implements PreProductService {

    @Autowired
    MaterialService materialService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    ProcedureMaterialService procedureMaterialService;

    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public boolean buildPreProduct(PreProduct preProduct) {
        // 二次生产
        if (preProduct.getId() != null) {
            PreProduct originalPreProduct = this.find(preProduct.getId());
            if (!reduce(originalPreProduct, preProduct)) {
                return false;
            }
            this.save(originalPreProduct);
        }
        Long buildProcedure = null;
        // 生产数量
        Long number = preProduct.getNumber();
        // 获取生产产品
        Product product = productService.find(preProduct.getProductId());
        String procedureIdString = product.getProcedureIds();
        boolean end = false;
        // 只有一道工序
        if (StringUtils.isNotBlank(procedureIdString) && !procedureIdString.contains(",")) {
            buildProcedure = Long.valueOf(procedureIdString);
            product.setNumber(preProduct.getNumber() + product.getNumber());
            preProduct.setDataDelete(ConstantUtils.DELETE);
        }
        if (StringUtils.isNotBlank(procedureIdString) && procedureIdString.contains(",")) {
            Long[] procedureIds = CommonUtils.string2Long(procedureIdString, ",");
            buildProcedure = procedureIds[preProduct.getProcedureStep() - 1];
            // 证明走到了最后一步
            if (procedureIds.length == preProduct.getProcedureStep()) {
                product.setNumber(preProduct.getNumber() + product.getNumber());
                end = true;
            }
        }
        if (build(buildProcedure, number)) {
            // 保存半成品
            List<PreProduct> preProductList = this.getPreProduct(preProduct);
            if (CollectionUtils.isNotEmpty(preProductList)) {
                preProduct.setId(preProductList.get(0).getId());
                preProduct.setNumber(preProduct.getNumber() + preProductList.get(0).getNumber());
            }
            // 保存半成品
            if (!end) {
                this.save(preProduct);
            }
            // 成品入库
            productService.save(product);
            return true;
        }
        return false;


    }

    @Override
    public List<PreProduct> getPreProduct(PreProduct preProduct) {
        PreProduct validate = new PreProduct();
        validate.setDataDelete(ConstantUtils.UN_DELETE);
        validate.setProcedureStep(preProduct.getProcedureStep());
        validate.setProductId(preProduct.getProductId());
        Example<PreProduct> preProductExample = Example.of(validate);
        return this.findAll(preProductExample, Sort.by("id"));
    }

    @Override
    public boolean nextProcedure(PreProduct preProduct) {
        Long buildProcedure = null;
        // 生产数量
        Long number = preProduct.getNumber();
        if (preProduct.getId() != null) {
            PreProduct originalPreProduct = this.find(preProduct.getId());
            if (!reduce(originalPreProduct, preProduct)) {
                return false;
            }
            this.save(originalPreProduct);
        }
        Product product = productService.find(preProduct.getProductId());
        String procedureIdString = product.getProcedureIds();
        if (StringUtils.isNotBlank(procedureIdString) && procedureIdString.contains(",")) {
            Long[] procedureIds = CommonUtils.string2Long(procedureIdString, ",");
            buildProcedure = procedureIds[preProduct.getProcedureStep() - 1];
            // 证明走到了最后一步
            if (procedureIds.length == preProduct.getProcedureStep()) {
                product.setNumber(preProduct.getNumber() + product.getNumber());
                preProduct.setDataDelete(ConstantUtils.DELETE);
            }
        }
        if (build(buildProcedure, number)) {
            // 保存半成品
            List<PreProduct> preProductList = this.getPreProduct(preProduct);
            if (CollectionUtils.isNotEmpty(preProductList)) {
                preProduct.setId(preProductList.get(0).getId());
                preProduct.setNumber(preProduct.getNumber() + preProductList.get(0).getNumber());
            }
            // 保存半成品
            this.save(preProduct);
            // 成品入库
            productService.save(product);
            return true;
        }
        return false;
    }

    /**
     * 工序减材料
     *
     * @param buildProcedureId 工序id
     * @param number           生产数量
     * @return
     */
    public boolean build(Long buildProcedureId, Long number) {
        // 获得工序 材料
        List<ProcedureMaterial> procedureMaterialList = procedureMaterialService.getProcedureMaterial(buildProcedureId);
        List<Material> materialList = new ArrayList<>();
        for (ProcedureMaterial procedureMaterial : procedureMaterialList) {
            Material material = materialService.find(procedureMaterial.getMaterialId());
            // 如果原材料的数量不够 则 返回false
            if (material.getNumber() < procedureMaterial.getNumber() * number) {
                return false;
            }
            material.setNumber(material.getNumber() - procedureMaterial.getNumber() * number);
            materialList.add(material);
        }
        if (CollectionUtils.isNotEmpty(materialList)) {
            materialService.saveCollection(materialList);
        }
        return true;
    }

    public boolean reduce(PreProduct preProduct, PreProduct updatePreProduct) {
        if (preProduct.getNumber() < updatePreProduct.getNumber()) {
            return false;
        } else {
            preProduct.setNumber(preProduct.getNumber() - updatePreProduct.getNumber());
            updatePreProduct.setId(null);
        }
        return true;
    }
}
