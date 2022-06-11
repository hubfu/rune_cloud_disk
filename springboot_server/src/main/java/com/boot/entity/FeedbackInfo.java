package com.boot.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class FeedbackInfo {
    private Integer id;

    private String userId;

    private String title;

    private String body;

    private Boolean isSolve;

    private String solution;

    private String sendDate;

    private String  solveDate;
    public void toConvert(Feedback feedback){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        this.id=feedback.getId();
        this.title= feedback.getTitle();
        this.isSolve=feedback.getIsSolve();
        this.solution= feedback.getSolution();
        this.sendDate=simpleDateFormat.format(feedback.getSendDate());
        this.userId=feedback.getUserId();
        this.body=feedback.getBody();
        if(feedback.getSolveDate()==null){
            this.solveDate="暂无信息";
        }else {
            this.solveDate=simpleDateFormat.format(feedback.getSolveDate());
        }

    }

}
