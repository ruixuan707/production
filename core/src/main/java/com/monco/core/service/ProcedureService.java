package com.monco.core.service;

import com.monco.core.entity.Material;
import com.monco.core.entity.Procedure;
import com.monco.core.entity.ProcedureMaterial;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:19
 * @Description:
 */
public interface ProcedureService extends BaseService<Procedure, Long> {

    /**
     * 保存工序
     *
     * @param procedure
     * @param materialList
     */
    void saveProcedure(Procedure procedure, List<Material> materialList);

    /**
     * 根据工序id 获得工序的所有材料及数量
     *
     * @param procedureId
     * @return
     */
    List<Material> getMaterialList(Long procedureId);
}
