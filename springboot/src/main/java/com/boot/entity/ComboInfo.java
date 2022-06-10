package com.boot.entity;


import lombok.Data;
import lombok.ToString;
import org.apache.commons.io.FileUtils;

@Data
@ToString
public class ComboInfo {
    private Integer id;

    private String name;

    private String  capacity;

    private Integer amount;

    private Integer time;

   public void toConvert(Combo combo){
       this.id=combo.getId();
       this.name=combo.getName();
       this.capacity= FileUtils.byteCountToDisplaySize(combo.getCapacity());
       this.time=combo.getTime();
       this.amount=combo.getAmount();
   }
}
