package com.boot;

import com.boot.utils.MD5Utils;
import com.boot.utils.UploadFileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class RuneCloudDiskApplicationTests {
    @Test
    void contextLoads() {
        String path="root1111";
        System.out.println(MD5Utils.getMD5("root","1234"));

    }

}
