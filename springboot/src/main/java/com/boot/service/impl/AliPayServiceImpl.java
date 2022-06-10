package com.boot.service.impl;

import com.boot.entity.Combo;
import com.boot.entity.ComboExample;
import com.boot.entity.Consume;
import com.boot.entity.Depot;
import com.boot.mapper.ComboMapper;
import com.boot.mapper.ConsumeMapper;
import com.boot.mapper.DepotMapper;
import com.boot.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AliPayServiceImpl implements AliPayService {
    @Autowired
    ComboMapper comboMapper;
    @Autowired
    ConsumeMapper consumeMapper;
    @Autowired
    DepotMapper depotMapper;
    @Override
    public List<Combo> selectAllCombo() {
        ComboExample example = new ComboExample();
        example.setDistinct(true);
        return comboMapper.selectByExample(example);
    }

    @Override
    public Combo selectACombo(Integer id) {
        return comboMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean insertAConsume(Consume consume) {
        if (consumeMapper.insert(consume)>0){
            return  true;
        }
        return false;
    }
//添加用户容量
    @Override
    public boolean expansionDepot(String userID, Long size) {
        Depot depot=new Depot();
        depot=depotMapper.selectByPrimaryKey(userID);
        depot.setBuyCapacity(depot.getBuyCapacity()+size);
        depot.setMaxCapacity(depot.getMaxCapacity()+size);
        if (depotMapper.updateByPrimaryKeySelective(depot)>0){
            return true;
        }
        return false;
    }
//购买次数
    @Override
    public boolean addComboSum(Combo combo) {
        combo.setBuySum(combo.getBuySum()+1);
        if ( comboMapper.updateByPrimaryKeySelective(combo)>0){
            return true;
        }

        return false;
    }
}
