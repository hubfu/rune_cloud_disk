package com.boot.mapper;

import com.boot.entity.ShareFile;
import com.boot.entity.ShareFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareFileMapper {
    int countByExample(ShareFileExample example);

    int deleteByExample(ShareFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShareFile record);

    int insertSelective(ShareFile record);

    List<ShareFile> selectByExample(ShareFileExample example);

    ShareFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShareFile record, @Param("example") ShareFileExample example);

    int updateByExample(@Param("record") ShareFile record, @Param("example") ShareFileExample example);

    int updateByPrimaryKeySelective(ShareFile record);

    int updateByPrimaryKey(ShareFile record);
}