package com.boot.mapper;

import com.boot.entity.Combo;
import com.boot.entity.ComboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ComboMapper {
    int countByExample(ComboExample example);

    int deleteByExample(ComboExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Combo record);

    int insertSelective(Combo record);

    List<Combo> selectByExample(ComboExample example);

    Combo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Combo record, @Param("example") ComboExample example);

    int updateByExample(@Param("record") Combo record, @Param("example") ComboExample example);

    int updateByPrimaryKeySelective(Combo record);

    int updateByPrimaryKey(Combo record);
}