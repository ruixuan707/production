package com.monco.core.page;

import com.monco.core.entity.Material;
import lombok.Data;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/12 20:27
 * @Description: 工序列表页面类
 */
@Data
public class ProcedurePage extends BasePage {

    private static final long serialVersionUID = -2154613752295618127L;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 工序上级
     */
    private Long parentId;

    /**
     * 材料集合
     */
    List<Material> materialList;
}
