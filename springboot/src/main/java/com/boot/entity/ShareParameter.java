package com.boot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShareParameter {
    private String fileId;
    private String userId;
    private Boolean isEncryptCode;
    private String extractionCode;
    private Integer shareTime;
    private String sharePath;
}
