package com.boot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StatisticsInfo {
    private Integer userNum;
    private Integer payUserNum;
    private Long userTotalCapacity;
    private Long userUsablePatitionSpace;
    private Long totalCapacity;
    private Long usablePatitionSpace;

}
