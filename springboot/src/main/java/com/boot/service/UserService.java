package com.boot.service;

import com.boot.entity.*;

import java.util.List;

public interface UserService {
    /**
     * 按id查找一位用户
     * @param userID
     * @return
     */
    public User selectAUser(String userID);

    /**
     * 添加一名新用户
     * @param user
     * @return
     */
    public boolean insertAUser(User user);

    /**
     * 更新该用户的信息
     * @param user
     * @return
     */
    public boolean updateAUserInfo(User user);

    /**
     * 查询该用户的所有消费记录
     * @param userID
     * @return
     */
    public List<Consume> selectAllConsumeInfo(String userID);

    /**
     * 查询该用户的文件仓库信息
     * @param userID
     * @return
     */
    public Depot selectDepotInfo(String userID);

    /**
     * 添加一条该用户的问题反馈
     *
     * @param feedback
     * @return
     */
    public boolean insertAFeedback(Feedback feedback);

    /**
     * 删除一条反馈
     * @param id
     * @return
     */

   public boolean deleteAFeedback(Integer id);

    /**
     * 查看该用户的所有反馈结果
     * @param userID
     * @return
     */
   public List<Feedback> selectUserAllFeedbackResult(String userID);



    /**
     * 添加用户的消费记录
     * @param consume
     * @return
     */
    public boolean insertUserConsumeInfo(Consume consume);

    /**
     * 查询该用户的所有消费记录信息
     * @param userID
     * @return
     */
    public List<Consume> selectUserAllConsumeInfo(String  userID);


    /**
     * 创建用户的文件仓库
     * @param depot
     * @return
     */
    /**
     * 创建一个文件仓库
     * @param depot
     * @return
     */
    public boolean insertUserDepot(Depot depot);

    /**
     * 更新用户的文件仓库信息
     * @param depot
     * @return
     */
    public boolean updateUserDepot(Depot depot);

    /**
     * 删除一条消费记录
     * @param id
     * @return
     */
    public boolean deleteAConsume(Integer id);

}
