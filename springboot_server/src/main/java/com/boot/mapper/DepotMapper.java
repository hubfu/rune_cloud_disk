package com.boot.mapper;

import com.boot.entity.Depot;
import com.boot.entity.DepotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepotMapper {
    int countByExample(DepotExample example);

    int deleteByExample(DepotExample example);

    int deleteByPrimaryKey(String id);

    int insert(Depot record);

    int insertSelective(Depot record);

    List<Depot> selectByExample(DepotExample example);

    Depot selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Depot record, @Param("example") DepotExample example);

    int updateByExample(@Param("record") Depot record, @Param("example") DepotExample example);

    int updateByPrimaryKeySelective(Depot record);

    int updateByPrimaryKey(Depot record);
}