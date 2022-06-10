package com.boot.mapper;

import com.boot.entity.Consume;
import com.boot.entity.ConsumeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumeMapper {
    int countByExample(ConsumeExample example);

    int deleteByExample(ConsumeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Consume record);

    int insertSelective(Consume record);

    List<Consume> selectByExample(ConsumeExample example);

    Consume selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Consume record, @Param("example") ConsumeExample example);

    int updateByExample(@Param("record") Consume record, @Param("example") ConsumeExample example);

    int updateByPrimaryKeySelective(Consume record);

    int updateByPrimaryKey(Consume record);
}