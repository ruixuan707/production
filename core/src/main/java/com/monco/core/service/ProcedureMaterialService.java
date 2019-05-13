package com.monco.core.service;

import com.monco.core.entity.Material;
import com.monco.core.entity.ProcedureMaterial;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:27
 * @Description:
 */
public interface ProcedureMaterialService extends BaseService<ProcedureMaterial, Long> {

    /**
     * 根据工序id 获得工序原料集合
     *
     * @param procedureId
     * @return
     */
    List<ProcedureMaterial> getProcedureMaterial(Long procedureId);
}
