package com.boot.entity;

import lombok.ToString;

@ToString
public class Combo {
    private Integer id;

    private String name;

    private Long capacity;

    private Integer amount;

    private Integer time;

    private Integer buySum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getBuySum() {
        return buySum;
    }

    public void setBuySum(Integer buySum) {
        this.buySum = buySum;
    }
}