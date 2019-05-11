package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sys_product")
public class Product extends BaseEntity<Long> {

    private static final long serialVersionUID = 2785016245334194212L;
}
