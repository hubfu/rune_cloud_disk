package com.boot.mapper;

import com.boot.entity.UploadFiles;
import com.boot.entity.UploadFilesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UploadFilesMapper {
    int countByExample(UploadFilesExample example);

    int deleteByExample(UploadFilesExample example);

    int deleteByPrimaryKey(String id);

    int insert(UploadFiles record);

    int insertSelective(UploadFiles record);

    List<UploadFiles> selectByExample(UploadFilesExample example);

    UploadFiles selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UploadFiles record, @Param("example") UploadFilesExample example);

    int updateByExample(@Param("record") UploadFiles record, @Param("example") UploadFilesExample example);

    int updateByPrimaryKeySelective(UploadFiles record);

    int updateByPrimaryKey(UploadFiles record);
}