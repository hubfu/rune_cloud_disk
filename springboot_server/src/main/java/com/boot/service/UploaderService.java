package com.boot.service;

import com.boot.entity.Chunk;
import com.boot.entity.UploadFiles;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UploaderService {
    /**
     * 得到文件MD5相同的文件
     * @param MD5
     * @return
     */
    List<UploadFiles> getFilesByMD5(String MD5);

    /**
     * 为前端返回一个上传文件的已上传的文件块的的集合，实现断点续传
     * @param chunk
     * @return
     */
    Map<String, Object> checkChunkExits(Chunk chunk);

    /**
     * 保存文件块
     * @param chunkNumber
     * @param identifier
     * @return
     */
    Integer saveChunk(Integer chunkNumber,String identifier);

    /**
     * 合并文件块
     * @param userId
     * @param fileName
     * @param chunkFolder
     * @return
     */
    String mergeFile(String userId,String fileName,String chunkFolder);

    /**
     * 下载文件
     * @param path
     * @param response
     */
    void downloadFile(String path, HttpServletResponse response);

    /**
     * 增加用户已使用的文件空间
     * @param depotId
     * @param size
     * @return
     */
    boolean addUsedCapacity(String depotId,Long size);

    /**
     * 用户的文件仓库容量是否足够
     * @param depotId
     * @param size
     * @return
     */
    boolean isOverflow(String depotId,Long size);
}
