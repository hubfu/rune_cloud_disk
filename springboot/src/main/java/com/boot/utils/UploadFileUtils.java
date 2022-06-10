package com.boot.utils;


import com.boot.entity.Chunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
public class UploadFileUtils {
    private static String[] textTypes={"text","doc","docx","pdf","ppt","pptx","xls","xlsx"};
    private static String[] imageTypes={"git","jpg","png","bmg","pic","tif"};
    private static String[] audioTypes={"wav","mp3","wma","rm","mid"};
    private static String[] videoTypes={"flv","avi","mov","mp4","wmv"};
    private static String[] comTypes={"rar","zip","7z","gz"};
//用时间戳生成随机文件名
    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = format.format(new Date());
        str += (int) ((Math.random() * 9 + 1) * 10000);

        return str;
    }


//获得文件后缀
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
//生成文件块的临时保存路径
    public static String generatePath(String uploadFolder, Chunk chunk) {

        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append(File.separator).append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return sb.append(File.separator)
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();

    }

    public static String getRelativePath(String path) {

        return path.substring(path.indexOf(File.separator));
    }
//压缩
    public static int compress(List<String> filePaths, String zipFilePath, Boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);
        //zip文件不存在，则创建文件，用于压缩
        if (!zipFile.exists())
            zipFile.createNewFile();

        int fileCount = 0;//记录压缩了几个文件？


        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < filePaths.size(); i++) {
                String relativePath = filePaths.get(i);
                if (StringUtils.isEmpty(relativePath)) {
                    continue;
                }
                File sourceFile = new File(relativePath);//绝对路径找到file
                if (sourceFile == null || !sourceFile.exists()) {
                    continue;
                }
                FileInputStream fis = new FileInputStream(sourceFile);
                if (keepDirStructure != null && keepDirStructure) {
                    //保持目录结构
                    zos.putNextEntry(new ZipEntry(relativePath));
                } else {
                    //直接放到压缩包的根目录
                    zos.putNextEntry(new ZipEntry(sourceFile.getName()));
                }
                //System.out.println("压缩当前文件："+sourceFile.getName());
                int len;
                try {
                    while ((len = fis.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }

                    fis.close();
                    zos.closeEntry();
                    log.info("关闭fis");
                }catch (IOException e){
                    e.printStackTrace();
                }

                fileCount++;
            }
            zos.close();
            log.info("zos关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileCount;
    }
    //读取zip文件内的文件,返回文件名称列表
    public static List<String> readZipFileName(String path){
        List<String> list = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                list.add(entries.nextElement().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
//根据后缀生成文件类型
public  static Integer getUploadFileType(String suffix){
        for (String type:textTypes){
            if (type.equals(suffix)){
                return 3;
            }
        }
    for (String type:imageTypes){
        if (type.equals(suffix)){
            return 4;
        }
    }
    for (String type:audioTypes){
        if (type.equals(suffix)){
            return 5;
        }
    }
    for (String type:videoTypes){
        if (type.equals(suffix)){
            return 6;
        }
    }
    for (String type:comTypes){
        if (type.equals(suffix)){
            return 7;
        }
    }
    return 1;
}


}
