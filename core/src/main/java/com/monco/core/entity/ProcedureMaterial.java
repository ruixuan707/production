package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:25
 * @Description: 工序和原材料关系表
 */
@Entity
@Getter
@Setter
@Table(name = "sys_procedure_material")
public class ProcedureMaterial extends BaseEntity<Long> {

    private static final long serialVersionUID = -539043324003782310L;

    /**
     * 工序id
     */
    private Long procedureId;

    /**
     * 原材料id
     */
    private Long materialId;

    /**
     * 原材料数量
     */
    private Long number;
}
