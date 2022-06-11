package com.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    public static final int SUCCESS=200;
    public static final int FAIL=400;
    public static  final int PARAMETER_NULL=500;
    public static final int NO_LOGIN=600;
    private int code=200;
    private Object model;
    private String message;

    public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
