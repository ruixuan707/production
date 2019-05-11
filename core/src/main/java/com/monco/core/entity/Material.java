package com.monco.core.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: monco
 * @Date: 2019/5/4 18:34
 * @Description: 物资材料类型表
 */
@Entity
@Getter
@Setter
@Table(name = "sys_material")
public class Material extends BaseEntity<Long> {

    private static final long serialVersionUID = 8824099838966935933L;

    /**
     * 材料名称
     */
    @Excel(name = "材料名称", orderNum = "1")
    private String materialName;

    /**
     * 材料类型
     */
    @Excel(name = "材料等级", orderNum = "2")
    private String materialLevel;

    /**
     * 材料规格
     */
    @Excel(name = "材料规格", orderNum = "3")
    private String materialNorms;

    /**
     * 单价
     */
    @Excel(name = "单价", orderNum = "4")
    private BigDecimal price;

    /**
     * 图片
     */
    @Excel(name = "图片", orderNum = "5")
    private String pic;

    /**
     * 库存数量
     */
    @Excel(name = "数量", orderNum = "6")
    private Long number;
}
