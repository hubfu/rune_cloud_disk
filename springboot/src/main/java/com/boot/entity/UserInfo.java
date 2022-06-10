package com.boot.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.io.FileUtils;

import java.text.SimpleDateFormat;

@Data
@ToString
public class UserInfo {
    private String id;
    private String name;
    private String  regDate;
    private String initialSize;
    private String buySize;
    private String usedSize;
    private String maxSize;
    public void toMerge(User user,Depot depot){
        this.id=user.getId();
        this.name=user.getName();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        this.regDate=simpleDateFormat.format(user.getRegdate());
        this.initialSize= FileUtils.byteCountToDisplaySize(depot.getMaxCapacity()-depot.getBuyCapacity());
        this.buySize=FileUtils.byteCountToDisplaySize(depot.getBuyCapacity());
        this.usedSize=FileUtils.byteCountToDisplaySize(depot.getUsedCapacity());
        this.maxSize=FileUtils.byteCountToDisplaySize(depot.getMaxCapacity());
    }
}
