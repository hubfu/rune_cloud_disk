package com.boot.mapper;

import com.boot.entity.PhotoAlbum;
import com.boot.entity.PhotoAlbumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PhotoAlbumMapper {
    int countByExample(PhotoAlbumExample example);

    int deleteByExample(PhotoAlbumExample example);

    int deleteByPrimaryKey(String id);

    int insert(PhotoAlbum record);

    int insertSelective(PhotoAlbum record);

    List<PhotoAlbum> selectByExample(PhotoAlbumExample example);

    PhotoAlbum selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PhotoAlbum record, @Param("example") PhotoAlbumExample example);

    int updateByExample(@Param("record") PhotoAlbum record, @Param("example") PhotoAlbumExample example);

    int updateByPrimaryKeySelective(PhotoAlbum record);

    int updateByPrimaryKey(PhotoAlbum record);
}