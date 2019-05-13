package com.monco.core.impl;

import com.monco.core.entity.Material;
import com.monco.core.entity.PreProduct;
import com.monco.core.entity.Procedure;
import com.monco.core.entity.ProcedureMaterial;
import com.monco.core.service.MaterialService;
import com.monco.core.service.PreProductService;
import com.monco.core.service.ProcedureMaterialService;
import com.monco.core.service.ProcedureService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public boolean buildPreProduct(PreProduct preProduct) {
        // 生产数量
        Long number = preProduct.getNumber();
        Long buildProcedure = preProduct.getBuildProcedure();
        if (build(buildProcedure, number)) {
            // 保存半成品
            this.save(preProduct);
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
}
