package com.boot.entity;

import lombok.Data;

@Data
public class ComboTable {
    private Integer id;

    private String name;

    private Long capacity;

    private Integer amount;

    private Integer time;

    private Integer buySum;
    private Integer totalSales;
    public void toConvert(Combo combo){
        this.id=combo.getId();
        this.name=combo.getName();
        this.amount=combo.getAmount();
        this.capacity=combo.getCapacity();
        this.time=combo.getTime();
        this.buySum=combo.getBuySum();
        this.totalSales=combo.getAmount()*combo.getBuySum();
    }
}
