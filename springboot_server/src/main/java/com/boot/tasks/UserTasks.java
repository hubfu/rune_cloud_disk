package com.boot.tasks;


import com.boot.entity.Combo;
import com.boot.entity.Consume;
import com.boot.entity.Depot;
import com.boot.entity.ShareGroup;
import com.boot.service.AdminService;
import com.boot.service.FileService;
import com.boot.service.UserService;
import com.boot.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class UserTasks {
    //检验套餐是否过期，每三个小时执行一次
@Scheduled(cron ="0 0 */3 * * ?")
public void CheckConsume(){
    AdminService adminService= (AdminService) ApplicationContextUtil.getBean("adminServiceimpl");
    UserService userService= (UserService) ApplicationContextUtil.getBean("userServiceImpl");
    List<Consume> consumeList=adminService.selectExpiredConsume();
    if (consumeList.size()==0){
        log.info("执行完毕");
        return;
    }
    for (Consume consume:consumeList){
        Combo combo= adminService.selectACombo(consume.getComboId());
        Depot depot= userService.selectDepotInfo(consume.getUserId());
        depot.setMaxCapacity(depot.getMaxCapacity()-combo.getCapacity());
        depot.setBuyCapacity(depot.getBuyCapacity()-combo.getCapacity());
        userService.updateUserDepot(depot);
        userService.deleteAConsume(consume.getId());
    }
    log.info("执行完毕");
}
//检验分享文件是否过期，每一天执行一次
@Scheduled(cron = "0 0 0 */1 * ?")
    public  void CheckShare(){
    FileService fileService= (FileService) ApplicationContextUtil.getBean("fileServiceImpl");
    List<ShareGroup> groups=fileService.getExpiredShare();
    if (groups.size()==0){
        log.info("执行完毕");
        return;
    }
    for (ShareGroup shareGroup:groups){
        fileService.deleteShareGroup(shareGroup.getId());
    }
    log.info("执行完毕");
}
}
