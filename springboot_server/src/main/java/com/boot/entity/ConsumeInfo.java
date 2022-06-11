package com.boot.entity;

import lombok.Data;

import java.text.SimpleDateFormat;

@Data

public class ConsumeInfo {
    private String  buyTime;
    private String totalAmount;
    private String subject;
    private String  expireDate;
    public void toConvert(Consume consume){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.buyTime=simpleDateFormat.format(consume.getBuyTime());
        this.expireDate=simpleDateFormat.format(consume.getExpireDate());
        this.totalAmount=consume.getTotalAmount();
        this.subject=consume.getSubject();
    }
}
