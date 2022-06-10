package com.boot.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AddShareUserInfo {
    private String id;
    private String userId;
    private Boolean isEncrypt;
    private String extractionCode;
    private String userPassword;
}
