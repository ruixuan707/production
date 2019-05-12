package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.ProcedureDao;
import com.monco.core.entity.Material;
import com.monco.core.entity.Procedure;
import com.monco.core.entity.ProcedureMaterial;
import com.monco.core.service.MaterialService;
import com.monco.core.service.ProcedureMaterialService;
import com.monco.core.service.ProcedureService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:19
 * @Description:
 */
@Service
public class ProcedureServiceImpl extends BaseServiceImpl<Procedure, Long> implements ProcedureService {

    @Autowired
    ProcedureDao procedureDao;

    @Autowired
    ProcedureMaterialService procedureMaterialService;

    @Autowired
    MaterialService materialService;

    @Override
    @Transactional
    public void saveProcedure(Procedure procedure, List<Material> materialList) {
        this.save(procedure);
        List<ProcedureMaterial> procedureMaterialList = new ArrayList<>();
        for (Material material : materialList) {
            ProcedureMaterial procedureMaterial = new ProcedureMaterial();
            procedureMaterial.setMaterialId(material.getId());
            procedureMaterial.setNumber(material.getNumber());
            procedureMaterial.setProcedureId(procedure.getId());
            procedureMaterialList.add(procedureMaterial);
        }
        if (CollectionUtils.isNotEmpty(procedureMaterialList)) {
            procedureMaterialService.saveCollection(procedureMaterialList);
        }

    }

    @Override
    public List<Material> getMaterialList(Long procedureId) {
        ProcedureMaterial procedureMaterial = new ProcedureMaterial();
        procedureMaterial.setDataDelete(ConstantUtils.UN_DELETE);
        procedureMaterial.setProcedureId(procedureId);
        Example<ProcedureMaterial> procedureMaterialExample = Example.of(procedureMaterial);
        List<ProcedureMaterial> procedureMaterialList = procedureMaterialService.findAll(procedureMaterialExample, Sort.by("id"));
        List<Material> materialList = new ArrayList<>();
        for (ProcedureMaterial procedureMaterialEntity : procedureMaterialList) {
            Material material = materialService.find(procedureMaterialEntity.getMaterialId());
            material.setNumber(procedureMaterialEntity.getNumber());
            materialList.add(material);
        }
        return materialList;
    }
}
