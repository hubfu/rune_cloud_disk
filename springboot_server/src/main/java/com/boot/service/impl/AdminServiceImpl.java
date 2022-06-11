package com.boot.service.impl;

import com.boot.entity.*;
import com.boot.mapper.*;
import com.boot.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Service("adminServiceimpl")
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ComboMapper comboMapper;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    ConsumeMapper consumeMapper;
    @Autowired
    DepotMapper depotMapper;
    @Override
    public Admin selectAAdmin(String name) {
        return adminMapper.selectByPrimaryKey(name);
    }

    @Override
    public boolean addAAdmin(Admin admin) {
        admin.setPermission(Boolean.FALSE);
        if (adminMapper.insert(admin)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> getAllAdmin() {
        AdminExample example=new AdminExample();
        example.createCriteria().andPermissionEqualTo(Boolean.FALSE);
        return adminMapper.selectByExample(example);
    }

    @Override
    public boolean adminNum() {
        AdminExample example=new AdminExample();
        example.setDistinct(true);
       if (adminMapper.countByExample(example)>=4){
           return true;
       }
        return false;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        if (adminMapper.updateByPrimaryKeySelective(admin)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<User> selectAllUser() {
        UserExample example=new UserExample();
        example.setDistinct(Boolean.TRUE);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<Depot> selectAllDepot() {
        DepotExample example=new DepotExample();
        example.setDistinct(Boolean.TRUE);
        return depotMapper.selectByExample(example);
    }

    @Override
    public List<User> selectUserLikeName(String name) {

        UserExample example=new UserExample();
        example.createCriteria().andNameLike("%"+name+"%");
        return userMapper.selectByExample(example);
    }

    @Override
    public boolean addCombo(Combo combo) {
        if (comboMapper.insertSelective(combo)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Combo> selectLeaderboard() {
        ComboExample example=new ComboExample();
        example.setOrderByClause("buy_sum DESC");
        return comboMapper.selectByExample(example);
    }

    @Override
    public List<Feedback> selectAllFeedback() {
        FeedbackExample example=new FeedbackExample();
        example.createCriteria().andIsSolveEqualTo(Boolean.FALSE);
        return feedbackMapper.selectByExample(example);
    }

    @Override
    public boolean updateFeedback(Feedback feedback) {
        if (feedbackMapper.updateByPrimaryKeySelective(feedback)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Consume> selectConsumeByUserId(String userId) {
        ConsumeExample example=new ConsumeExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return consumeMapper.selectByExample(example);
    }
//查找过期的消费记录
    @Override
    public List<Consume> selectExpiredConsume() {
        ConsumeExample example=new ConsumeExample();
        example.createCriteria().andExpireDateLessThanOrEqualTo(new Date());
        return consumeMapper.selectByExample(example);
    }

    @Override
    public Combo selectACombo(Integer id) {
        return comboMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Combo> getAllCombo() {
        ComboExample example=new ComboExample();
        example.setDistinct(Boolean.TRUE);
        return comboMapper.selectByExample(example);
    }

    @Override
    public boolean updateCombo(Combo combo) {
        if (comboMapper.updateByPrimaryKeySelective(combo)>0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean deleteACombo(Integer comboId) {
        if (comboMapper.deleteByPrimaryKey(comboId)>0){
            return true;
        }
        return false;
    }

    @Override
    public User selectAUser(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Depot selectADepot(String id) {
        return depotMapper.selectByPrimaryKey(id);
    }
//获得系统信息
    @Override
    public StatisticsInfo getStatisticsInfo() {
        StatisticsInfo statisticsInfo=new StatisticsInfo();
        DepotExample example=new DepotExample();
        example.setDistinct(Boolean.TRUE);
        List<Depot> depots=depotMapper.selectByExample(example);
        statisticsInfo.setUserNum(depots.size());
        log.info("size:"+depots.size());
        statisticsInfo.setPayUserNum(0);
        Long userTotalCapacity=0L;
        Long userUsablePatitionSpace=0L;
        Integer payNum=0;
        for (Depot depot:depots){
            log.info("depot:"+depot.toString());
            userTotalCapacity+= depot.getMaxCapacity();
            userUsablePatitionSpace+= depot.getUsedCapacity();
            if (depot.getBuyCapacity()>0){

                payNum++;
            }
        }
        statisticsInfo.setUserTotalCapacity(userTotalCapacity);
        statisticsInfo.setUserUsablePatitionSpace(userUsablePatitionSpace);
        statisticsInfo.setPayUserNum(payNum);
        File file=new File("D:");
        statisticsInfo.setTotalCapacity(file.getTotalSpace());
        statisticsInfo.setUsablePatitionSpace(file.getUsableSpace());

        return statisticsInfo;
    }

    @Override
    public boolean updateDepotByUserID(Depot depot) {
        if (depotMapper.updateByPrimaryKeySelective(depot)>0){
            return true;
        }
        return false;
    }

}
