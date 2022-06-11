package com.boot.service;

import com.boot.entity.*;

import java.util.List;

public interface AdminService {
//    查找管理员
    public Admin selectAAdmin(String name);
//    添加管理员
    public boolean addAAdmin(Admin admin);
//    查找所有的管理员
    public List<Admin>getAllAdmin();
//    统计管理员的数量
    public boolean adminNum();
//    更新管理员信息
    public boolean updateAdmin(Admin admin);
//    查找所有的用户
    public List<User> selectAllUser();
//查找所有的文件仓库
    public List<Depot> selectAllDepot();
    public List<User> selectUserLikeName(String name);
    public boolean addCombo(Combo combo);
    public List<Combo> selectLeaderboard();
    public List<Feedback> selectAllFeedback();
    public boolean updateFeedback(Feedback feedback);
    public List<Consume> selectConsumeByUserId(String userId);
    public List<Consume>selectExpiredConsume();
    public Combo selectACombo(Integer id);
    public List<Combo> getAllCombo();
    public boolean updateCombo(Combo combo);
    public boolean deleteACombo(Integer comboId);
    public User selectAUser(String id);
    public Depot selectADepot(String id);
    public StatisticsInfo getStatisticsInfo();
    public boolean updateDepotByUserID(Depot depot);
}
