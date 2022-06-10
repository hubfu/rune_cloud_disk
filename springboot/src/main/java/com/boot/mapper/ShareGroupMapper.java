package com.boot.mapper;

import com.boot.entity.ShareGroup;
import com.boot.entity.ShareGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareGroupMapper {
    int countByExample(ShareGroupExample example);

    int deleteByExample(ShareGroupExample example);

    int deleteByPrimaryKey(String id);

    int insert(ShareGroup record);

    int insertSelective(ShareGroup record);

    List<ShareGroup> selectByExample(ShareGroupExample example);

    ShareGroup selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ShareGroup record, @Param("example") ShareGroupExample example);

    int updateByExample(@Param("record") ShareGroup record, @Param("example") ShareGroupExample example);

    int updateByPrimaryKeySelective(ShareGroup record);

    int updateByPrimaryKey(ShareGroup record);
}