package com.boot;

import com.boot.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan(basePackages = "com.boot.mapper")
@Slf4j
public class RuneCloudDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuneCloudDiskApplication.class, args);
       log.info("系统启动：{}",MD5Utils.getMD5("root","1234"));
    }

}
