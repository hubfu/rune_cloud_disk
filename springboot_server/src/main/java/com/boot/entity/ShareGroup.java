package com.boot.entity;

import java.util.Date;

public class ShareGroup {
    private String id;

    private String userId;

    private Date endTime;

    private Boolean isEncrypt;

    private String extractionCode;

    private String sharePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(Boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getExtractionCode() {
        return extractionCode;
    }

    public void setExtractionCode(String extractionCode) {
        this.extractionCode = extractionCode == null ? null : extractionCode.trim();
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath == null ? null : sharePath.trim();
    }
}