package com.boot.controller;


import com.boot.entity.*;
import com.boot.service.AdminService;
import com.boot.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public CommonResult longin(@RequestBody Admin admin) {
        log.info("admin" + admin.toString());
        Admin newAdmin = adminService.selectAAdmin(admin.getName());
        if (newAdmin== null) {
            return new CommonResult(CommonResult.FAIL, "账户不存在");
        }
        String password= MD5Utils.getMD5(admin.getName(),admin.getPassword());
        if (password.equals(newAdmin.getPassword())) {
            return new CommonResult(CommonResult.SUCCESS, newAdmin, "登录成功");
        }
        return new CommonResult(CommonResult.FAIL, "账户或密码错误");
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public CommonResult AddAdmin(@RequestBody Admin admin) {
        if (adminService.adminNum()) {
            return new CommonResult(CommonResult.FAIL, "管理员数量达到上限");
        }
        String password=MD5Utils.getMD5(admin.getName(),admin.getPassword());
        admin.setPassword(password);
        log.info("newAdmin:{}",admin.toString());
        if (adminService.addAAdmin(admin)) {

            return new CommonResult(CommonResult.SUCCESS, "生成成功");
        }
        return new CommonResult(CommonResult.FAIL, "生成失败");
    }

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @PutMapping("/update")
    public CommonResult updateAdmin(@RequestBody Admin admin) {
        log.info("admin:{}",admin.toString());
        String password=MD5Utils.getMD5(admin.getName(),admin.getPassword());
        admin.setPassword(password);
        if (adminService.updateAdmin(admin)) {
            return new CommonResult(CommonResult.SUCCESS, "更新成功");
        }
        return new CommonResult(CommonResult.FAIL, "更新失败");
    }

    /**
     * 查询所有的管理员
     * @return
     */
    @GetMapping("/all")
    CommonResult getAllAdmin() {
        List<Admin> adminList = adminService.getAllAdmin();
        if (adminList != null) {
            return new CommonResult(CommonResult.SUCCESS, adminList, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 查询所有的用户信息
     * @param pageNum
     * @return
     */
    @GetMapping("/user/info/{pageNum}")
    public CommonResult getUserInfo(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<User> users = adminService.selectAllUser();
        List<Depot> depots = adminService.selectAllDepot();
        List<UserInfo> userInfos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserInfo userInfo = new UserInfo();
            if (users.get(i).getId().equals(depots.get(i).getId())) {
                userInfo.toMerge(users.get(i), depots.get(i));
            }
            userInfos.add(userInfo);
        }
        PageInfo pageInfo = new PageInfo<>(users);
        pageInfo.setList(userInfos);
        return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
    }

    /**
     * 查询用户的消费记录
     * @param userId
     * @return
     */
    @GetMapping("/user/consume/{userId}")
    public CommonResult getUserConsume(@PathVariable String userId) {
        List<Consume> consumeList = adminService.selectConsumeByUserId(userId);
        List<ConsumeInfo> consumeInfos = new ArrayList<>();
        if (consumeList != null) {
            for (Consume consume : consumeList) {
                ConsumeInfo consumeInfo = new ConsumeInfo();
                consumeInfo.toConvert(consume);
                consumeInfos.add(consumeInfo);
            }
            return new CommonResult(CommonResult.SUCCESS, consumeInfos, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 查找反馈
     * @param pageNum
     * @return
     */
    @GetMapping("/feedback/{pageNum}")
    public CommonResult getAllFeedback(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Feedback> feedbackList = adminService.selectAllFeedback();
        List<FeedbackInfo> feedbackInfoList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackInfo feedbackInfo = new FeedbackInfo();
            feedbackInfo.toConvert(feedback);
            feedbackInfoList.add(feedbackInfo);
        }
        PageInfo pageInfo = new PageInfo<>(feedbackList);
        pageInfo.setList(feedbackInfoList);
        if (feedbackList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 解决反馈
     * @param feedback
     * @return
     */
    @PutMapping("/feedback/solve")
    public CommonResult solveFeedback(@RequestBody Feedback feedback) {
        feedback.setIsSolve(Boolean.TRUE);
        feedback.setSolveDate(new Date());
        if (adminService.updateFeedback(feedback)) {
            return new CommonResult(CommonResult.SUCCESS, "解决成功");
        }
        return new CommonResult(CommonResult.FAIL, "解决失败");
    }

    /**
     *套餐排行榜
     * @param pageNum
     * @return
     */
    @GetMapping("/leaderboard/{pageNum}")
    public CommonResult getLeaderbord(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Combo> comboList = adminService.selectLeaderboard();
        PageInfo pageInfo = new PageInfo<>(comboList);
        if (comboList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 添加套餐
     * @param combo
     * @return
     */
    @PostMapping("/combo/add")
    public CommonResult addCombo(@RequestBody Combo combo) {
        combo.setCapacity(combo.getCapacity() * 1024 * 1024 * 1024);
        if (adminService.addCombo(combo)) {
            return new CommonResult(CommonResult.SUCCESS, "添加成功");
        }
        return new CommonResult(CommonResult.FAIL, "添加失败");
    }

    /**
     * 查找套餐
     * @param pageNum
     * @return
     */
    @GetMapping("/combo/all/{pageNum}")
    public CommonResult getAllCombo(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Combo> comboList = adminService.getAllCombo();
        log.info("combo:" + comboList.toString());
        List<ComboInfo> comboInfoList = new ArrayList<>();
        for (Combo combo : comboList) {
            ComboInfo comboInfo = new ComboInfo();
            comboInfo.toConvert(combo);
            comboInfoList.add(comboInfo);
        }
        log.info("info:" + comboInfoList.toString());
        PageInfo pageInfo = new PageInfo<>(comboList);
        pageInfo.setList(comboInfoList);

        if (comboList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 修改套餐
     * @param combo
     * @return
     */
    @PutMapping("/combo/update")
    public CommonResult updateCombo(@RequestBody Combo combo) {
        combo.setCapacity(combo.getCapacity() * 1024 * 1024 * 1024);
        if (adminService.updateCombo(combo)) {
            return new CommonResult(CommonResult.SUCCESS, "更新成功");
        }
        return new CommonResult(CommonResult.FAIL, "更新失败");
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @DeleteMapping("combo/delete/{id}")
    public CommonResult deleteCombo(@PathVariable Integer id) {
        log.info("id:" + id);
        if (adminService.deleteACombo(id)) {
            return new CommonResult(CommonResult.SUCCESS, "删除成功");
        }
        return new CommonResult(CommonResult.FAIL, "删除失败");
    }

    /**
     * 套餐排行榜
     * @return
     */
    @GetMapping("/combo/leaderboard")
    public CommonResult getComboLeaderboard() {
        List<Combo> comboList = adminService.selectLeaderboard();
        List<ComboTable> comboTables = new ArrayList<>();
        for (Combo combo : comboList) {
            ComboTable comboTable = new ComboTable();
            comboTable.toConvert(combo);
            comboTables.add(comboTable);
        }
        if (comboList != null) {
            return new CommonResult(CommonResult.SUCCESS, comboTables, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    /**
     * 查找用户
     * @param userId
     * @return
     */
 @GetMapping("user/{userId}")
    public CommonResult getAUser(@PathVariable String userId){
        User user=adminService.selectAUser(userId);
        Depot depot= adminService.selectADepot(userId);
        UserInfo userInfo=new UserInfo();
        userInfo.toMerge(user,depot);
        List<UserInfo> userInfos=new ArrayList<>();
        userInfos.add(userInfo);
        if (user!=null){
            return new CommonResult(CommonResult.SUCCESS,userInfos,"查找成功");
        }
        return new CommonResult(CommonResult.FAIL,"查找失败");
 }

    /**
     * 系统信息统计
     * @return
     */
 @GetMapping("/statistics")
    public CommonResult getStatistics(){
        StatisticsInfo statisticsInfo= adminService.getStatisticsInfo();
        log.info("static:"+statisticsInfo.toString());
        if (statisticsInfo!=null){
            return new CommonResult(CommonResult.SUCCESS,statisticsInfo,"查询成功");
        }
     return new CommonResult(CommonResult.FAIL,"查询失败");
 }

    /**
     * 修改用户存储空间
     * @param depot
     * @return
     */
 @PutMapping("/update/depot")
    public CommonResult updateDepot(@RequestBody Depot depot){
        long buy=depot.getBuyCapacity()*1024*1024*1024L;
        long max=5*1024*1024*1024L+buy;
        depot.setBuyCapacity(buy);
        depot.setMaxCapacity(max);
        depot.setUsedCapacity(null);
        if (adminService.updateDepotByUserID(depot)){
            return new CommonResult(CommonResult.SUCCESS,"更新成功");
        }
        return new CommonResult(CommonResult.FAIL,"更新失败");
 }
}
