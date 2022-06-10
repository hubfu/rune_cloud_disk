package com.boot.service;

import com.boot.entity.Combo;
import com.boot.entity.Consume;

import java.util.List;

public interface AliPayService {
    /**
     * 查找所有的套餐
     * @return
     */
    public List<Combo>selectAllCombo();

    /**
     * 查找一条套餐
     * @param id
     * @return
     */
    public Combo selectACombo(Integer id);

    /**
     * 插入一条信息
     * @param consume
     * @return
     */
    public boolean insertAConsume(Consume consume);

    /**
     * 增加用户文件仓库的容量
     * @param userID
     * @param size
     * @return
     */
    public  boolean expansionDepot(String userID,Long size);
    public boolean addComboSum(Combo combo);
}
