package com.boot.service.impl;

import com.boot.base.RedisDao;
import com.boot.entity.Chunk;
import com.boot.entity.Depot;
import com.boot.entity.UploadFiles;
import com.boot.entity.UploadFilesExample;
import com.boot.mapper.DepotMapper;
import com.boot.mapper.UploadFilesMapper;
import com.boot.service.UploaderService;
import com.boot.utils.UploadFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
@Slf4j
public class UploaderServiceImpl implements UploaderService {
    @Autowired
    private RedisDao redisDao;
    @Value("${file.uploadpath.merge}")
    private String mergeFolder;
    @Autowired
    DepotMapper depotMapper;
    @Autowired
    UploadFilesMapper uploadFilesMapper;
    @Override
    public List<UploadFiles> getFilesByMD5(String MD5) {
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andMd5EqualTo(MD5);

        return uploadFilesMapper.selectByExample(example);
    }
//检验文件块是否存在
    @Override
    public Map<String, Object> checkChunkExits(Chunk chunk) {
        log.info("进入R-check");
        Map<String, Object> res = new HashMap<>();
        String identifier = chunk.getIdentifier();
//        判断Redis中key为identifier项是否存在，存在就返回
        if (redisDao.existsKey(identifier)) {
            Set<Integer> chunkNumber = (Set<Integer>) redisDao.hmGet(identifier, "chunkNumberList");
            res.put("chunkNumbers", chunkNumber);
        }
        log.info("res:" + res.toString());
        return res;
    }

    @Override
    public Integer saveChunk(Integer chunkNumber, String identifier) {
        log.info("进入R-save");
        Set<Integer> oldChunkNumber = (Set<Integer>) redisDao.hmGet(identifier, "chunkNumberList");
        if (Objects.isNull(oldChunkNumber)) {
            Set<Integer> newChunkNumber = new HashSet<>();
            newChunkNumber.add(chunkNumber);
            redisDao.hmSet(identifier, "chunkNumberList", newChunkNumber);
            return newChunkNumber.size();
        } else {
            oldChunkNumber.add(chunkNumber);
            redisDao.hmSet(identifier, "chunkNumberList", oldChunkNumber);
            return oldChunkNumber.size();
        }
    }

    @Override
    public String mergeFile(String userId,String fileName, String chunkFolder) {
        String newMergeFolder=mergeFolder+File.separator+userId;
        String target = newMergeFolder +File.separator + UploadFileUtils.getFileName() + "_" + fileName;
        log.info("进入R-merge");
        log.info("chunkFolder:{}", chunkFolder);
        try {
            //目标路径是否存在，不存在就创建
            if (!Files.isWritable(Paths.get(newMergeFolder))) {
                Files.createDirectories(Paths.get(newMergeFolder));
            }

            log.info("target:{}", target);
            Files.createFile(Paths.get(target));
//            遍历存放文件块的路径
            Files.list(Paths.get(chunkFolder))
                    .filter(path -> path.getFileName().toString().contains("-"))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(target), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            log.info("delete{}",chunkFolder);
            Files.delete(Paths.get(chunkFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return target;
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) {
        try {
            InputStream inputStream = new FileInputStream(Paths.get(path).toFile());
            response.reset();
//            设置文件的类型
            response.setContentType("application/octet-stream");
            String filename = Paths.get(path).toFile().getName();
// 设置编码格式
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            inputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//添加使用的空间
    @Override
    public boolean addUsedCapacity(String depotId, Long size) {
       Depot depot=depotMapper.selectByPrimaryKey(depotId);
       depot.setUsedCapacity(depot.getUsedCapacity()+size);
       if (depotMapper.updateByPrimaryKeySelective(depot)>0){
           return true;
       }
        return false;
    }

    @Override
    public boolean isOverflow(String depotId, Long size) {
        Depot depot=depotMapper.selectByPrimaryKey(depotId);
        if ((depot.getMaxCapacity()-depot.getUsedCapacity())>size){
            return true;
        }
        return false;
    }
}
