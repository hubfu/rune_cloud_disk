package com.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MD5Utils {
//    用户密码MD5加密
    public static String getMD5(String ID,String Pwd){
        String md5=ID+Pwd;
        log.info("md5:{}",md5);
        String result= DigestUtils.md5DigestAsHex(md5.getBytes());
        log.info("result1:{},length:{}",result,result.length());
        result=DigestUtils.md5DigestAsHex(result.getBytes());
        log.info("result2:{},length:{}",result,result.length());
        return result;
    }
}
