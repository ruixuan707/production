package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.ProcedureMaterial;
import com.monco.core.service.ProcedureMaterialService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:27
 * @Description:
 */
@Service
public class ProcedureMaterialServiceImpl extends BaseServiceImpl<ProcedureMaterial, Long> implements ProcedureMaterialService {

    @Override
    public List<ProcedureMaterial> getProcedureMaterial(Long procedureId) {
        ProcedureMaterial procedureMaterial = new ProcedureMaterial();
        procedureMaterial.setProcedureId(procedureId);
        procedureMaterial.setDataDelete(ConstantUtils.UN_DELETE);
        Example<ProcedureMaterial> procedureMaterialExample = Example.of(procedureMaterial);
        List<ProcedureMaterial> procedureMaterialList = this.findAll(procedureMaterialExample, Sort.by("id"));
        return procedureMaterialList;
    }
}
