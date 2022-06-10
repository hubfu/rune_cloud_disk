package com.boot.utils;

import com.boot.entity.UploadFileInfo;
import com.boot.entity.UploadFiles;

import java.util.ArrayList;
import java.util.List;

public class EntityToConvertUtils {
//    格式化上传文件类
    public  static List<UploadFileInfo> filesListToConvert(List<UploadFiles> uploadFiles){
        List<UploadFileInfo>infoList = new ArrayList<>();
        for (UploadFiles files:uploadFiles){
            UploadFileInfo info=new UploadFileInfo();
            info.toConvert(files);
            infoList.add(info);
        }
        return infoList;
    }
}
