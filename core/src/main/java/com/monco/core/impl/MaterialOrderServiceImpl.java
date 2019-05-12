package com.monco.core.impl;

import com.monco.core.dao.MaterialOrderDao;
import com.monco.core.entity.Material;
import com.monco.core.entity.MaterialOrder;
import com.monco.core.service.MaterialOrderService;
import com.monco.core.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialOrderServiceImpl extends BaseServiceImpl<MaterialOrder, Long> implements MaterialOrderService {

    @Autowired
    MaterialOrderDao materialOrderDao;

    @Autowired
    MaterialService materialService;

    @Override
    @Transactional
    public void saveMaterialOrder(MaterialOrder materialOrder) {
        this.save(materialOrder);
        Material material = materialService.find(materialOrder.getMaterialId());
        material.setNumber(material.getNumber() + materialOrder.getNumber());
    }

}
