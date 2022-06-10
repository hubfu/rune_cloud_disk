package com.boot.controller;

import com.boot.entity.*;

import com.boot.service.AdminService;
import com.boot.service.UserService;

import com.boot.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;
    @Autowired
    AdminService adminService;
    //登录
    @PostMapping("/login")
    public CommonResult login(@RequestBody User user) {
        log.info("用户:"+user.toString());
        User newUser = service.selectAUser(user.getId());
        if (newUser.getId()==null){
            return new CommonResult(CommonResult.FAIL, "该用户不存在");
        }
        String password= MD5Utils.getMD5(user.getId(),user.getPassword());
        if (password.equals(newUser.getPassword())) {
            return new CommonResult(CommonResult.SUCCESS, newUser, "登录成功");
        }

        return new CommonResult(CommonResult.FAIL, "账户或密码错误");

    }

    //注册
    @PostMapping("/register")
    public CommonResult register(@RequestBody User newUser) {
        log.info("USER:" + newUser.toString());
        Date regDate = new Date();
        newUser.setRegdate(regDate);
        String password=MD5Utils.getMD5(newUser.getId(),newUser.getPassword());
        newUser.setPassword(password);
        StatisticsInfo statisticsInfo=new StatisticsInfo();
        statisticsInfo=adminService.getStatisticsInfo();
        log.info("剩余空间:{}",statisticsInfo.getUsablePatitionSpace());
        if (statisticsInfo.getUsablePatitionSpace()<5*1024*1024*1024L){
            return  new CommonResult(CommonResult.FAIL,"注册功能暂时关闭");
        }
        if (service.insertAUser(newUser)) {
            return new CommonResult(CommonResult.SUCCESS, "注册成功");
        }
        return new CommonResult(CommonResult.FAIL, "注册失败请重新注册");
    }
//    保险箱验证
   @PostMapping("/safe/login")
   public CommonResult safeLogin(@RequestBody User user){
        User newUser=service.selectAUser(user.getId());
        if (user.getSafePassword().equals(newUser.getSafePassword())){
            return new  CommonResult(CommonResult.SUCCESS,"验证成功");
        }
        return new CommonResult(CommonResult.FAIL,"验证失败");
   }
    //查询用户信息
    @GetMapping("/info/{id}")
    public CommonResult getUserinfo(@PathVariable("id") String id) {
        User user = service.selectAUser(id);
        Depot depot = service.selectDepotInfo(id);

        if (user != null && depot != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.toMerge(user, depot);
            return new CommonResult(CommonResult.SUCCESS, userInfo, "查找成功");
        }
        return new CommonResult(CommonResult.FAIL, "为查到该用户的相关信息");
    }

    //更新用户信息
    @PutMapping("/update")
    public CommonResult updateUserInfo(@RequestBody User user) {
        log.info("user" + user.toString());
        if (user.getPassword()!=null){
            String password=MD5Utils.getMD5(user.getId(),user.getPassword());
            user.setPassword(password);
        }

        log.info("nUser:"+user.toString());
        if (service.updateAUserInfo(user)) {
            return new CommonResult(CommonResult.SUCCESS, "修改成功");

        }
        return new CommonResult(CommonResult.FAIL, "修改失败");
    }

    //    查询用户消费记录
    @GetMapping("/consume/record/{id}/{pageNum}")
    public CommonResult getConsumeRecord(@PathVariable String id, @PathVariable Integer pageNum) {
        log.info("id:" + id + "-----num:" + pageNum);
        PageHelper.startPage(pageNum, 5);
        List<Consume> consumeList = service.selectUserAllConsumeInfo(id);
        List<ConsumeInfo> consumeInfos = new ArrayList<>();
        for (int i = 0; i < consumeList.size(); i++) {
            ConsumeInfo consumeInfo = new ConsumeInfo();
            consumeInfo.toConvert(consumeList.get(i));
            consumeInfos.add(consumeInfo);
        }
        PageInfo pageInfo = new PageInfo<>(consumeList);
        pageInfo.setList(consumeInfos);


        if (consumeList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //添加一条反馈
    @PostMapping("/feedback")
    public CommonResult insertFeedback(@RequestBody Feedback feedback) {
        log.info("fedback:" + feedback.toString());
        feedback.setSendDate(new Date());
        if (service.insertAFeedback(feedback)) {
            return new CommonResult(CommonResult.SUCCESS, "发送成功");

        }
        return new CommonResult(CommonResult.FAIL, "发送失败");
    }
//删除反馈
    @DeleteMapping("/feedback/delete/{id}")
    public CommonResult deleteFeedback(@PathVariable Integer id) {
        if (service.deleteAFeedback(id)){
            return  new CommonResult(CommonResult.SUCCESS,"删除成功");
        }
        return new CommonResult(CommonResult.FAIL,"删除失败");
    }

    //    查询用户的反馈
    @GetMapping("/feedback/result/{id}/{pageNum}")
    public CommonResult selectFeedbackResult(@PathVariable String id, @PathVariable Integer pageNum) {
//        log.info("id:"+id+"-----num:"+pageNum);
        PageHelper.startPage(pageNum, 5);
        List<Feedback> feedbacks = service.selectUserAllFeedbackResult(id);
        List<FeedbackInfo> feedbackInfos = new ArrayList<>();
        for (int i = 0; i < feedbacks.size(); i++) {
            FeedbackInfo feedbackInfo = new FeedbackInfo();
            feedbackInfo.toConvert(feedbacks.get(i));
            feedbackInfos.add(feedbackInfo);
        }
        PageInfo pageInfo = new PageInfo(feedbacks);
        pageInfo.setList(feedbackInfos);
        if (feedbacks != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询失败");

        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //    查询用户的文件仓库信息
    @GetMapping("/depot/info/{id}")
    public CommonResult selectDepotInfo(@PathVariable String id) {
        Depot depot = service.selectDepotInfo(id);
        if (depot != null) {
            return new CommonResult(CommonResult.SUCCESS, depot, "查询成功");

        }
        return new CommonResult(CommonResult.FAIL, "查询失败");

    }

}
