package com.boot.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class ShareInfo {
    private String id;

    private String userId;

    private String endTime;

    private Boolean isEncrypt;

    private String extractionCode;

    private String sharePath;
    public void toConvert(ShareGroup shareGroup){
        this.id=shareGroup.getId();
        this.userId= shareGroup.getUserId();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd-HH");
        this.endTime=simpleDateFormat.format(shareGroup.getEndTime());
        this.isEncrypt=shareGroup.getIsEncrypt();
        this.extractionCode=shareGroup.getExtractionCode();
        this.sharePath=shareGroup.getSharePath();
    }
}
