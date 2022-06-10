package com.boot.entity;

import com.boot.utils.UploadFileUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.text.SimpleDateFormat;

@Data
@Slf4j
public class UploadFileInfo {
    private String id;

    private String name;

    private String folder;

    private String depot;
    private Integer type;

    private String  size;

    private String uploadDate;

    private Integer downloadSum;

    private Boolean isCollect;
    private String path;

    private String modifiedDate;
    public void toConvert(UploadFiles files){
        this.id=files.getId();
        this.name=files.getName();
        this.folder=files.getFolder();
        this.type=files.getType();
        if (files.getType()!=1) {
            this.size = FileUtils.byteCountToDisplaySize(files.getSize());
        }
        this.downloadSum=files.getDownloadSum();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        this.uploadDate=simpleDateFormat.format(files.getUploadDate());
        this.modifiedDate=simpleDateFormat.format(files.getModifiedDate());
        this.depot=files.getDepot();
        this.isCollect=files.getIsCollect();
        this.path= UploadFileUtils.getRelativePath(files.getPath());
//        log.info("path:{}",this.path);
    }
}
