package com.boot.entity;

public class Depot {
    private String id;

    private Long usedCapacity;

    private Long maxCapacity;

    private Long buyCapacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Long usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public Long getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Long getBuyCapacity() {
        return buyCapacity;
    }

    public void setBuyCapacity(Long buyCapacity) {
        this.buyCapacity = buyCapacity;
    }
}