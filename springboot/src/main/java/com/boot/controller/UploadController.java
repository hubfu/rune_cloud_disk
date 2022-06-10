package com.boot.controller;

import com.boot.base.RedisDao;
import com.boot.base.impl.RedisDaoImpl;
import com.boot.entity.Chunk;
import com.boot.entity.CommonResult;
import com.boot.entity.UploadFiles;
import com.boot.service.FileService;
import com.boot.service.UploaderService;
import com.boot.utils.UploadFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/upload")
@RestController
@Slf4j
public class UploadController {
    @Autowired
    private UploaderService uploaderService;
    @Autowired
    private FileService fileService;
    @Value("${file.uploadpath.chunk}")
    private String chunkFolder;
    @Autowired
    RedisDao redisDao;
    //判断用户容量是否足够
    @GetMapping("/detect/{depotId}/{fileSize}")
    public CommonResult detectSize(@PathVariable String depotId,@PathVariable Long fileSize){
        if (uploaderService.isOverflow(depotId,fileSize)){
            return  new CommonResult(CommonResult.SUCCESS,"容量足够");
        }
        return new CommonResult(CommonResult.FAIL,"容量不足");
    }
    //检验相同MD5的文件，实现秒传
    @GetMapping("/check/md5/{userId}/{MD5}")
    public CommonResult checkMD5(@PathVariable String userId,@PathVariable String MD5) {
        log.info("进入秒传");
        UploadFiles uploadFiles=new UploadFiles();
        List<UploadFiles> uploadFilesList= uploaderService.getFilesByMD5(MD5);
          if (uploadFilesList.size()>0){

              uploadFiles=uploadFilesList.get(0);
              uploadFiles.setId(UploadFileUtils.getFileName());
              uploadFiles.setFolder(userId);
              uploadFiles.setUploadDate(new Date());
              uploadFiles.setModifiedDate(uploadFiles.getUploadDate());
              uploadFiles.setDepot(userId);
              uploadFiles.setIsSafe(Boolean.FALSE);
              uploadFiles.setIsCollect(Boolean.FALSE);
              uploadFiles.setIsRecycle(Boolean.FALSE);
              uploadFiles.setMd5(MD5);
              fileService.addNewFile(uploadFiles);
              uploaderService.addUsedCapacity(userId, uploadFiles.getSize());
              return new CommonResult(CommonResult.SUCCESS,"秒传成功");
          }


        return new CommonResult(CommonResult.FAIL,"不进行秒传");
    }
    //检验文件块，断点续传
    @GetMapping("/chunk")
    public Map<String, Object> checkChunk(Chunk chunk){
        log.info("chunk:{}",chunk.toString());
        log.info("进入 get chunk");

        return  uploaderService.checkChunkExits(chunk);

    }
    //保存文件块
    @PostMapping("/chunk")
    public String  saveChunk( Chunk chunk){
        log.info("进入 post chunk");
        log.info("chunk:"+chunk.toString());
        MultipartFile file=chunk.getFile();

        Integer chunkNumber=chunk.getChunkNumber();
        String identifier=chunk.getIdentifier();
        byte[] bytes;
        try {
            bytes= file.getBytes();
            Path path= Paths.get(UploadFileUtils.generatePath(chunkFolder,chunk));
            log.info("path:{}",path);
            Files.write(path,bytes);
            log.info("文件写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
     Integer chunks=uploaderService.saveChunk(chunkNumber,identifier);
        log.info("chunks:{}",chunks);

        if (chunks.equals(chunk.getTotalChunks())){

          return "上传成功";

        }

        return "上传中";
    }
    //合并文件块
    @PostMapping("/merge")
    public String mergeChunk(Chunk chunk){
        log.info("进入 merge");
        log.info("id:"+chunk.getIdentifier());
        String fileName=chunk.getFilename();
        log.info("filename:{}",fileName);
        String filepath=uploaderService.mergeFile(chunk.getUserid(),fileName,chunkFolder+ File.separator+chunk.getIdentifier());
        log.info("文件合并成功");
        UploadFiles newUploadFile=new UploadFiles();
        newUploadFile.setId(UploadFileUtils.getFileName());
        newUploadFile.setName(chunk.getFilename());
        newUploadFile.setUploadDate(new Date());
        newUploadFile.setDepot(chunk.getUserid());
        newUploadFile.setSize(chunk.getTotalSize());
        newUploadFile.setFolder(chunk.getUserid());
        newUploadFile.setModifiedDate(newUploadFile.getUploadDate());
        newUploadFile.setPath(filepath);
        newUploadFile.setSuffix(UploadFileUtils.getSuffix(chunk.getFilename()));
        newUploadFile.setMd5(chunk.getIdentifier());
        fileService.addNewFile(newUploadFile);
        uploaderService.addUsedCapacity(chunk.getUserid(), chunk.getTotalSize());

        String identifier=chunk.getIdentifier();
        redisDao.hmDel(identifier,"chunkNumberList");
       return "合并成功";
    }
    //下载文件
    @PostMapping("/download")
    public void downloadFile(@RequestBody UploadFiles file, HttpServletResponse  response){
        UploadFiles files=fileService.getAFile(file.getId());
        uploaderService.downloadFile(files.getPath(),response);
    }
}
